package com.alessoft.ConnectionManager;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import org.hibernate.annotations.GenericGenerator;
import lombok.Data;

@Data
@Entity
public class Connection {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "id", unique = true)
    private String id;

    private String type;
    private String name;
    private String host;
    private int port;
    private String username;
    private String password;
    @Lob
    private String privateKey;
    private String privateKeyPassword;

}
