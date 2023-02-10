package com.mybank.model;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Uni;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Cacheable
public class Transact extends PanacheEntityBase {

    private static final Logger logger = LoggerFactory.getLogger(Transact.class);

    private static final String  ACC_IBAN   = "account_iban";
    private static final String  AMOUNT     = "amount";
    private static final String  ASCENDING  = "ASC";
    private static final String  DESCENDING = "DESC";

    @Id @Column(name = "id", nullable = false) public Long id;

    //@Column(length = 40, unique = true)
    //TODO: Unique?  i think so
    @Column
    public String reference;

    @Column
    public String account_iban;

    @Column
    public LocalDateTime date;

    @Column
    public BigDecimal amount;


    @Column
    public BigDecimal fee;

    @Column
    public String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Find by account iban, sorted
     *
     * @param accountIban Acount iban
     * @param sortAmount Ascending or Descending
     * @return list List filtered by account id
     */
    public static Uni<List<Transact>> findByAccountIban(String accountIban, String sortAmount){
        Sort.Direction direction;
        if(DESCENDING.equalsIgnoreCase(sortAmount)){
            direction= Sort.Direction.Descending;
        }else if(ASCENDING.equalsIgnoreCase(sortAmount)){
            direction= Sort.Direction.Ascending;
        }else {
            //TODO: log warng?
            direction= Sort.Direction.Ascending;
        }
        Sort sort = Sort.by(AMOUNT,direction);
        Uni<List<Transact>> list = Transact.list(ACC_IBAN,sort,accountIban);
        if(logger.isDebugEnabled()){
            logger.debug("findByAccountIban with account_iban: {} -> list: {}",accountIban, list );
        }
        return list;
    }


}