package com.shopapp.batch.reader;

import com.shopapp.data.entity.CurrencyEntity;
import com.shopapp.data.entitydto.in.CurrencyEntityDtoIn;
import com.shopapp.data.entitydto.out.UserDtoOut;
import lombok.AllArgsConstructor;
import org.springframework.batch.item.adapter.ItemReaderAdapter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.File;
import java.util.UUID;

@Component
@AllArgsConstructor
public class Readers {

    private DataSource dataSource;

    /* @StepScope//when reading job parameter, these two annotations are required
     @Bean
     public FlatFileItemReader<StudentCsv> flatFileItemReader(
             @Value("#{jobParameters['inputFile']}") FileSystemResource fileSystemResource) {
         FlatFileItemReader<StudentCsv> flatFileItemReader =
                 new FlatFileItemReader<StudentCsv>();*/
    public FlatFileItemReader<CurrencyEntityDtoIn> flatFileItemReader() {

        System.out.println("flatFileItemReader");
        var reader = new FlatFileItemReader<CurrencyEntityDtoIn>();
        reader.setResource(new FileSystemResource(
                new File("C:\\Users\\ppetr\\code\\spring-mvc-ws-petr\\inputFiles\\currencies.csv")));

        reader.setLineMapper(new DefaultLineMapper<>() {
            {
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames("name", "symbol", "code");
                        setDelimiter(",");
                    }
                });
                setFieldSetMapper(new BeanWrapperFieldSetMapper<>() {
                    {
                        setTargetType(CurrencyEntityDtoIn.class);
                    }
                });

            }
        });

        reader.setLinesToSkip(1);
        return reader;
    }

    //reads from external HTTP calls
    public ItemReaderAdapter<UserDtoOut> itemReaderAdapter() {
        var reader = new ItemReaderAdapter<UserDtoOut>();
        reader.setTargetObject("batchService");
        reader.setTargetMethod("restCallToGetUsers");
        //sets method arguments
        //reader.setArguments(new Object[]{"","",""});
        reader.setArguments(new Object[]{});
        return reader;
    }

    public JdbcCursorItemReader<CurrencyEntity> jdbcCursorItemReader() {
        var reader = new JdbcCursorItemReader<CurrencyEntity>();
        reader.setDataSource(dataSource);
        reader.setSql("SELECT * FROM currencies");
        reader.setRowMapper((resultSet, i) -> {
            CurrencyEntity currency = new CurrencyEntity();
            currency.setId(UUID.nameUUIDFromBytes(resultSet.getBytes("id")));
            currency.setCode(resultSet.getString("code"));
            currency.setName(resultSet.getString("name"));
            currency.setSymbol(resultSet.getString("symbol"));
            return currency;
        });
        return reader;
    }

    public JsonItemReader<CurrencyEntityDtoIn> jsonItemReader() {
    /*@StepScope
    @Bean
    public JsonItemReader<CurrencyEntityDtoIn> jsonItemReader(
            @Value("#{jobParameters['inputFile']}") FileSystemResource fileSystemResource) {*/

        System.out.println("jsonItemReader");
        var jsonItemReader = new JsonItemReader<CurrencyEntityDtoIn>();
        jsonItemReader.setResource(new FileSystemResource(
                new File("C:\\Users\\ppetr\\code\\spring-mvc-ws-petr\\inputFiles\\currencies.json")));

        jsonItemReader.setJsonObjectReader(new JacksonJsonObjectReader<>(CurrencyEntityDtoIn.class));

        jsonItemReader.setMaxItemCount(100);
        jsonItemReader.setCurrentItemCount(0);

        return jsonItemReader;
    }

    public StaxEventItemReader<CurrencyEntityDtoIn> staxEventItemReader() {
        //stax = streaming API for XML
        System.out.println("staxEventItemReader");
        var reader = new StaxEventItemReader<CurrencyEntityDtoIn>();
        reader.setResource(new FileSystemResource(
                new File("C:\\Users\\ppetr\\code\\spring-mvc-ws-petr\\inputFiles\\currencies.xml")));
        reader.setFragmentRootElementName("currencyentitydtoin");
        reader.setUnmarshaller(new Jaxb2Marshaller() {
            {
                setClassesToBeBound(CurrencyEntityDtoIn.class);
            }
        });

        return reader;
    }
}
