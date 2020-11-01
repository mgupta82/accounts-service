package com.anz.accounts.repository.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class Transaction {
    @Id
    private String transId;
    private String accountNumber;
    private Date valueDate;
    private String currency;
    private BigDecimal amount;
    private String transNarrative;
}
