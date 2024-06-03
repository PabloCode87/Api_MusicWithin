package com.ejemplos.modelo;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
@Entity
public class Subscription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subscriptionID;

    @ManyToOne
    @JoinColumn(name = "userID")
    private Usuario usuario;

    private Date start_date;

    private Date end_date;

    private String payment_method;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Enumerated(EnumType.STRING)
    private SubscriptionTypeEnum subscription_type;
    
    public enum StatusEnum {
        ACTIVO,
        CANCELADO,
        PENDIENTE_DE_PAGO
    }
    
    public enum SubscriptionTypeEnum {
        PREMIUM,
        GRATUITA
    }

}
