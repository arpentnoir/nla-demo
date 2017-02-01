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
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.user.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
class LoanController {

  private static final String VIEWS_LOAN_CREATE_OR_UPDATE_FORM = "loans/createOrUpdateLoanForm";
  private final LoanRepository loans;


  @Autowired
  public LoanController(LoanRepository clinicService) {
    this.loans = clinicService;
  }

  @InitBinder
  public void setAllowedFields(WebDataBinder dataBinder) {
    dataBinder.setDisallowedFields("id");
  }

  @RequestMapping(value = "/loans/new", method = RequestMethod.GET)
  public String initCreationForm(User user, ModelMap model) {
    Loan loan = new Loan();
    user.addLoan(loan);
    model.put("loan", loan);
    return VIEWS_LOAN_CREATE_OR_UPDATE_FORM;
  }

  @RequestMapping(value = "/loans/new", method = RequestMethod.POST)
  public String processCreationForm(User user, Loan loan, BindingResult result, ModelMap model) {
//    if (StringUtils.hasLength(pet.getName()) && pet.isNew() && owner.getPet(pet.getName(), true) != null){
//      result.rejectValue("name", "duplicate", "already exists");
//    }
    if (result.hasErrors()) {
      model.put("loan", loan);
      return VIEWS_LOAN_CREATE_OR_UPDATE_FORM;
    } else {
      user.addLoan(loan);
      this.loans.save(loan);
      return "redirect:/owners/{ownerId}";
    }
  }

  @RequestMapping(value = "/loans/find", method = RequestMethod.GET)
  public String initFindForm(Map<String, Object> model) {
    model.put("loan", new Loan());
    return "loans/findLoans";
  }

  @RequestMapping(value = "/all-loans", method = RequestMethod.GET)
  public String processFindForm(Loan loan, BindingResult result, Map<String, Object> model) {

    Collection<Loan> results = this.loans.findAll();

      model.put("selections", results);
      return "loans/loansList";

  }

  @RequestMapping(value = "/users/{userID}/loans", method = RequestMethod.GET)
  public String processFindForm(User user, BindingResult result, Map<String, Object> model) {


    // find users by last name
    Collection<Loan> results = this.loans.findByUserId(user.getId());
    if (results.isEmpty()) {
      // no users found
      result.rejectValue("lastName", "notFound", "not found");
      return "loans/loansList";
    } else {
      // multiple users found
      model.put("selections", results);
      return "loans/loansList";
    }
  }

}
