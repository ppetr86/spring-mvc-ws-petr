package com.shopapp.data.entity.order;

import com.shopapp.data.entity.superclass.IdBasedEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "order_track")
@NoArgsConstructor
@Getter
@Setter
public class OrderTrackEntity extends IdBasedEntity {
	
	@Column(length = 256)
	private String notes;

	private Date updatedTime;

	@Enumerated(EnumType.STRING)
	@Column(length = 45, nullable = false)
	private OrderStatusEntity status;

	@ManyToOne
	@JoinColumn(name = "order_id")
	private OrderEntity order;
	
	@Transient
	public String getUpdatedTimeOnForm() {
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
		//dateFormatter.setTimeZone(java.util.TimeZone.getTimeZone("Asia/Istanbul"));
		return dateFormatter.format(this.updatedTime);
	}
	
	public void setUpdatedTimeOnForm(String dateString) throws ParseException {
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
		//dateFormatter.setTimeZone(java.util.TimeZone.getTimeZone("Asia/Istanbul"));
		try {
			this.updatedTime = dateFormatter.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
	}

}
