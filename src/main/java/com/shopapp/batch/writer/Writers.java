package com.shopapp.batch.writer;

import com.shopapp.data.entity.CurrencyEntity;
import com.shopapp.data.entity.superclass.IdBasedEntity;
import com.shopapp.data.entitydto.in.AbstractIdBasedDtoIn;
import com.shopapp.data.entitydto.out.AbstractIdBasedDtoOut;
import com.shopapp.data.entitydto.out.CurrencyEntityDtoOut;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.adapter.ItemWriterAdapter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.UUID;

@Component
@AllArgsConstructor
public class Writers {

    private DataSource dataSource;

    public ItemWriter<AbstractIdBasedDtoIn> abstractIdBasedDtoInItemWriter() {
        return chunk -> {
            System.out.println("inside AbstractDtoInItemWriter");
            for (var each : chunk) {
                System.out.println(each);
            }
        };
    }

    public ItemWriter<AbstractIdBasedDtoOut> abstractIdBasedDtoOutItemWriter() {
        return chunk -> {
            System.out.println("inside AbstractIdBasedDtoOutItemWriter");
            chunk.forEach(System.out::println);
        };
    }

    private byte[] convertUUIDToBytes(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }

    public FlatFileItemWriter<CurrencyEntityDtoOut> flatFileItemWriter() {

        var writer = new FlatFileItemWriter<CurrencyEntityDtoOut>();

        writer.setResource(new FileSystemResource(
                new File("C:\\Users\\ppetr\\code\\spring-mvc-ws-petr\\outputFiles\\currencies.csv")));

        writer.setHeaderCallback(writer1 -> writer1.write("id,name,symbol,code"));
        writer.setLineAggregator(new DelimitedLineAggregator<>() {
            {
                setDelimiter(",");//delimiter must be the same as in setHeaderCallback
                setFieldExtractor(new BeanWrapperFieldExtractor<>() {
                    {
                        setNames(new String[]{"id", "name", "symbol", "code"});
                    }
                });
            }
        });
        writer.setFooterCallback(writer12 -> writer12.write("Created at " + new Date()));
        return writer;
    }

    public ItemWriter<IdBasedEntity> idBasedEntityItemWriter() {
        return chunk -> {
            System.out.println("inside IdBasedItemWriter");
            for (var each : chunk) {
                System.out.println(each);
            }
        };
    }

    public ItemWriterAdapter<CurrencyEntityDtoOut> itemWriterAdapter() {
        var writer = new ItemWriterAdapter<CurrencyEntityDtoOut>();
        writer.setTargetObject("batchService");
        writer.setTargetMethod("restCallToCreateCurrencies");
        writer.setTargetObject(CurrencyEntityDtoOut.class);
        return writer;
    }


    /*@Bean
    public JdbcBatchItemWriter<? extends IdBasedEntity> jdbcBatchItemWriter1() {
        JdbcBatchItemWriter<IdBasedEntity> jdbcBatchItemWriter =
                new JdbcBatchItemWriter<IdBasedEntity>();

        jdbcBatchItemWriter.setDataSource(batchConfig.dataSource());
        jdbcBatchItemWriter.setSql(
                "insert into student(id, first_name, last_name, email) "
                        + "values (?,?,?,?)");

        jdbcBatchItemWriter.setItemPreparedStatementSetter(
                new ItemPreparedStatementSetter<IdBasedEntity>() {

                    @Override
                    public void setValues(IdBasedEntity item, PreparedStatement ps) throws SQLException {
                        ps.setBytes(1, convertUUIDToBytes(item.getId()));
                        *//*ps.setString(2, item.getFirstName());
                        ps.setString(3, item.getLastName());
                        ps.setString(4, item.getEmail());*//*
                    }
                });

        return jdbcBatchItemWriter;
    }*/

    public JdbcBatchItemWriter<CurrencyEntity> jdbcBatchItemWriter() {
        JdbcBatchItemWriter<CurrencyEntity> jdbcBatchItemWriter = new JdbcBatchItemWriter<>();

        jdbcBatchItemWriter.setDataSource(dataSource);
        jdbcBatchItemWriter.setSql("insert into currencies(id, name, symbol, code) values (?, ?, ?, ?)");

        jdbcBatchItemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());

        jdbcBatchItemWriter.setItemPreparedStatementSetter((currency, ps) -> {
            ps.setBytes(1, convertUUIDToBytes(currency.getId()));
            ps.setString(2, currency.getName());
            ps.setString(3, currency.getSymbol());
            ps.setString(4, currency.getCode());
        });

        return jdbcBatchItemWriter;
    }

    public JsonFileItemWriter<CurrencyEntity> jsonFileItemWriter() {
        var writer = new JsonFileItemWriter<CurrencyEntity>(
                new FileSystemResource(
                        new File("C:\\Users\\ppetr\\code\\spring-mvc-ws-petr\\outputFiles\\currencies.json")),
                new JacksonJsonObjectMarshaller<>());

        return writer;
    }

    public ItemWriter<Long> longItemWriter() {
        return chunk -> {
            System.out.println("inside FirstItemWriter");
            chunk.forEach(System.out::println);
        };
    }

    public StaxEventItemWriter<? extends IdBasedEntity> staxEventItemWriter() {

        /*@StepScope
        @Bean
        public StaxEventItemWriter<StudentJdbc> staxEventItemWriter(
                @Value("#{jobParameters['outputFile']}") FileSystemResource fileSystemResource) {*/
        var writer = new StaxEventItemWriter<IdBasedEntity>();

        writer.setResource(new FileSystemResource(
                new File("C:\\Users\\ppetr\\code\\spring-mvc-ws-petr\\outputFiles\\currencies.xml")));

        writer.setRootTagName("students");

        writer.setMarshaller(new Jaxb2Marshaller() {
            {
                setClassesToBeBound(IdBasedEntity.class);
            }
        });
        return writer;
    }
}
