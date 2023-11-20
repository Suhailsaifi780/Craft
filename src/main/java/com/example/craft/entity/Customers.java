package com.example.craft.entity;

import com.example.craft.model.dto.request.CreateProfileRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "customers")
public class Customers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "legal_name")
    private String legalName;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "address_line1")
    private String addressLine1;

    @Column(name = "address_line2")
    private String addressLine2;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zip")
    private Integer zip;

    @Column(name = "country")
    private String country;

    @Column(name = "legal_address")
    private String legalAddress;

    @Column(name = "pan_number")
    private String panNumber;

    @Column(name = "ein_number")
    private String einNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "website")
    private String website;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SubscribedProducts> subscribedProducts;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    public Customers(CreateProfileRequest createProfileRequest) {
        this.legalName = createProfileRequest.getLegal_name();
        this.companyName = createProfileRequest.getCompany_name();
        this.addressLine1 = createProfileRequest.getBusiness_address().getAddress_line1();
        this.addressLine2 = createProfileRequest.getBusiness_address().getAddress_line2();
        this.city = createProfileRequest.getBusiness_address().getCity();
        this.state = createProfileRequest.getBusiness_address().getState();
        this.zip = createProfileRequest.getBusiness_address().getZip();
        this.country = createProfileRequest.getBusiness_address().getCountry();
        this.legalAddress = createProfileRequest.getLegal_address();
        this.panNumber = createProfileRequest.getPan_number();
        this.einNumber = createProfileRequest.getEin_number();
        this.email = createProfileRequest.getEmail();
        this.website = createProfileRequest.getWebsite();
    }
}