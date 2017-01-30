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

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends Repository<User, Integer> {

    /**
     * Retrieve {@link User}s from the data store by last name, returning all users
     * whose last name <i>starts</i> with the given name.
     * @param lastName Value to search for
     * @return a Collection of matching {@link User}s (or an empty Collection if none
     * found)
     */
    @Query("SELECT DISTINCT user FROM User user WHERE user.lastName LIKE :lastName%")
    @Transactional(readOnly = true)
    Collection<User> findByLastName(@Param("lastName") String lastName);

    /**
     * Retrieve an {@link User} from the data store by id.
     * @param id the id to search for
     * @return the {@link User} if found
     */
    @Query("SELECT user FROM User user WHERE user.id =:id")
    @Transactional(readOnly = true)
    User findById(@Param("id") Integer id);

    /**
     * Save an {@link User} to the data store, either inserting or updating it.
     * @param user the {@link User} to save
     */
    void save(User user);


}
