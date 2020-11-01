package com.anz.accounts.repository.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "account")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class Account {
    @Id
    private String accountNumber;
    private String userId;
    private String accountName;
    private String accountType;
    private String currency;
    private Date balanceDate;
    private BigDecimal availableBalance;
}
