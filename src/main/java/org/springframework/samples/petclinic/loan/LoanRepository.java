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
package org.springframework.samples.petclinic.loan;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface LoanRepository extends Repository<Loan, Integer> {


  @Query("SELECT loan FROM Loan loan WHERE loan.user.getID() =:id")
  @Transactional(readOnly = true)
  Collection<Loan> findByUserId(@Param("id") Integer id);


  @Query("SELECT loan FROM Loan loan")
  @Transactional(readOnly = true)
  Collection<Loan> findAll();

  void save(Loan loan);


}
