package com.shopapp.data.entity.order;

import com.shopapp.data.entity.AbstractAddress;
import com.shopapp.data.entity.AddressEntity;
import com.shopapp.data.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@Getter
@Setter
public class OrderEntity extends AbstractAddress implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderEntity.class);

    @Column(nullable = false, length = 45)
    private String country;

    private Date orderTime;

    private float shippingCost;
    private float productCost;
    private float subtotal;
    private float tax;
    private float total;

    private int deliverDays;
    private Date deliverDate;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private OrderStatusEntity status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderDetailEntity> orderDetails = new HashSet<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("updatedTime ASC")
    private List<OrderTrackEntity> orderTracks = new ArrayList<>();

    public OrderEntity(UUID id, Date orderTime, float productCost, float subtotal, float total) {
        this.id = id;
        this.orderTime = orderTime;
        this.productCost = productCost;
        this.subtotal = subtotal;
        this.total = total;
    }

    public void copyAddressFromCustomer() {
        setFirstName(user.getAddress().getFirstName());
        setLastName(user.getAddress().getLastName());
        setPhoneNumber(user.getAddress().getPhoneNumber());
        setAddressLine1(user.getAddress().getAddressLine1());
        setAddressLine2(user.getAddress().getAddressLine2());
        setCity(user.getAddress().getCity());
        setCountry(user.getAddress().getCountry());
        setPostalCode(user.getAddress().getPostalCode());
    }

    public void copyShippingAddress(AddressEntity address) {
        setFirstName(address.getFirstName());
        setLastName(address.getLastName());
        setPhoneNumber(address.getPhoneNumber());
        setAddressLine1(address.getAddressLine1());
        setAddressLine2(address.getAddressLine2());
        setCity(address.getCity());
        setCountry(address.getCountry());
        setPostalCode(address.getPostalCode());
    }

    @Transient
    public String getDeliverDateOnForm() {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        //dateFormatter.setTimeZone(java.util.TimeZone.getTimeZone("Asia/Istanbul"));
        return dateFormatter.format(this.deliverDate);
    }

    public void setDeliverDateOnForm(String dateString) {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        //dateFormatter.setTimeZone(java.util.TimeZone.getTimeZone("Asia/Istanbul"));
        try {
            this.deliverDate = dateFormatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Transient
    public String getDestination() {
        String destination = city + ", ";
        destination += country;

        return destination;
    }

    @Transient
    public String getProductNames() {
        String productNames = "";

        productNames = "<ul>";

        for (OrderDetailEntity detail : orderDetails) {
            productNames += "<li>" + detail.getProduct().getShortName() + "</li>";
        }

        productNames += "</ul>";

        return productNames;
    }

    @Transient
    public String getRecipientAddress() {
        String address = addressLine1;

        if (addressLine2 != null && !addressLine2.isEmpty()) address += ", " + addressLine2;

        if (!city.isEmpty()) address += ", " + city;

        address += ", " + country;

        if (!postalCode.isEmpty()) address += ". " + postalCode;

        return address;
    }

    @Transient
    public String getRecipientName() {
        String name = firstName;
        if (lastName != null && !lastName.isEmpty()) name += " " + lastName;
        return name;
    }

    @Transient
    public String getShippingAddress() {
        String address = firstName;

        if (lastName != null && !lastName.isEmpty()) address += " " + lastName;

        if (!addressLine1.isEmpty()) address += ", " + addressLine1;

        if (addressLine2 != null && !addressLine2.isEmpty()) address += ", " + addressLine2;

        if (!city.isEmpty()) address += ", " + city;

        address += ", " + country;

        if (!postalCode.isEmpty()) address += ". Postal Code: " + postalCode;
        if (!phoneNumber.isEmpty()) address += ". Phone Number: " + phoneNumber;

        return address;
    }

    public boolean hasStatus(OrderStatusEntity status) {

        LOGGER.info("Order | hasStatus is called");

        LOGGER.info("Order | hasStatus | status : " + status.toString());

        for (OrderTrackEntity aTrack : orderTracks) {
            if (aTrack.getStatus().equals(status)) {

                LOGGER.info("Order | hasStatus | return True ");

                return true;
            }
        }

        LOGGER.info("Order | hasStatus | return False ");

        return false;
    }

    @Transient
    public boolean isCOD() {
        return paymentMethod.equals(PaymentMethod.COD);
    }

    @Transient
    public boolean isDelivered() {
        return hasStatus(OrderStatusEntity.DELIVERED);
    }

    @Transient
    public boolean isPicked() {
        return hasStatus(OrderStatusEntity.PICKED);
    }

    @Transient
    public boolean isProcessing() {
        return hasStatus(OrderStatusEntity.PROCESSING);
    }

    @Transient
    public boolean isReturnRequested() {
        return hasStatus(OrderStatusEntity.RETURN_REQUESTED);
    }

    @Transient
    public boolean isReturned() {
        return hasStatus(OrderStatusEntity.RETURNED);
    }

    @Transient
    public boolean isShipping() {
        return hasStatus(OrderStatusEntity.SHIPPING);
    }

    @Override
    public String toString() {
        return "Order [id=" + id + ", subtotal=" + subtotal + ", paymentMethod=" + paymentMethod + ", status=" + status
                + ", customer=" + user.getFullName() + "]";
    }
}
