/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.user;

import javax.persistence.*;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.core.style.ToStringCreator;
import org.springframework.samples.petclinic.loan.Loan;
import org.springframework.samples.petclinic.model.Person;

import java.util.*;


@Entity
@Table(name = "users")
public class User extends Person {

    @Column(name = "telephone")
    @NotEmpty
    @Digits(fraction = 0, integer = 10)
    private String telephone;

    @Column(name = "email")
    @NotEmpty
    @Email
    private String email;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Loan> loans;

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    protected Set<Loan> getLoansInternal() {
        if (this.loans == null) {
            this.loans = new HashSet<>();
        }
        return this.loans;
    }

    protected void setLoansInternal(Set<Loan> loans) {
        this.loans = loans;
    }

    public List<Loan> getLoans() {
        List<Loan> sortedLoans = new ArrayList<>(getLoansInternal());
        PropertyComparator.sort(sortedLoans, new MutableSortDefinition("id", true, true));
        return Collections.unmodifiableList(sortedLoans);
    }

    public void addLoan(Loan loan) {

        getLoansInternal().add(loan);
        loan.setUser(this);
    }
    @Override
    public String toString() {
        return firstName + ' ' + lastName;
    }
}
