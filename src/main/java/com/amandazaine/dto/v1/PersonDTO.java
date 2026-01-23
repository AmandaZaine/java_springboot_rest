package com.amandazaine.dto.v1;

import com.amandazaine.serializer.GenderSerializer;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@JsonPropertyOrder({"id", "gender", "primeiro_nome", "lastName"})
//@JsonFilter("PersonFilter")
public class PersonDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    @JsonProperty("primeiro_nome")
    private String firstName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String lastName;

    @JsonFormat(pattern = "dd/MM/YYYY")
    private Date  dateOfBirth;

    @JsonIgnore
    private String address;

    @JsonSerialize(using = GenderSerializer.class)
    private String gender;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String phoneNumber;

    public PersonDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PersonDTO personDTO = (PersonDTO) o;
        return getId() == personDTO.getId() && Objects.equals(getFirstName(), personDTO.getFirstName()) && Objects.equals(getLastName(), personDTO.getLastName()) && Objects.equals(getDateOfBirth(), personDTO.getDateOfBirth()) && Objects.equals(getAddress(), personDTO.getAddress()) && Objects.equals(getGender(), personDTO.getGender()) && Objects.equals(getPhoneNumber(), personDTO.getPhoneNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getDateOfBirth(), getAddress(), getGender(), getPhoneNumber());
    }
}
