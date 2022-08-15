package com.todeb.bkolay.creditapplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.todeb.bkolay.creditapplication.exception.handler.GenericExceptionHandler;
import com.todeb.bkolay.creditapplication.model.dto.CustomerDTO;
import com.todeb.bkolay.creditapplication.model.entity.Customer;
import com.todeb.bkolay.creditapplication.model.mapper.CreditMapper;
import com.todeb.bkolay.creditapplication.model.mapper.CustomerMapper;
import com.todeb.bkolay.creditapplication.service.CustomerService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    private CustomerMapper customerMapper;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
        mvc = MockMvcBuilders.standaloneSetup(customerController).setControllerAdvice(new GenericExceptionHandler()).build();
    }


    @Test
    void getAllCustomer() throws Exception{
        // init test values / given
        List<Customer> expectedCustomers = getSampleTestCustomers();

        // stub - when
        Mockito.when(customerService.getAllCustomer()).thenReturn(expectedCustomers);

        MockHttpServletResponse response = mvc.perform(get("/customer/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        List<Customer> actualCustomers = new ObjectMapper().readValue(response.getContentAsString(), new TypeReference<List<Customer>>() {
        });
        assertEquals(expectedCustomers.size(), actualCustomers.size());
    }

    @Test
    void getCustomerByIdentityNumber() throws Exception {

        // init test values / given
        List<Customer> expectedCustomers = getSampleTestCustomers();

        // stub - when
        Mockito.when(customerService.getCustomerByIdentityNumber("24506231362")).thenReturn(expectedCustomers.get(0));

        MockHttpServletResponse response = mvc.perform(get("/customer/24506231362")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        Customer actualCustomer = new ObjectMapper().readValue(response.getContentAsString(), Customer.class);
        Assert.assertEquals(expectedCustomers.get(0).getName(),actualCustomer.getName());
    }

    @Test
    void createNewCustomer() throws Exception{
        //init
        Customer customer = getSampleTestCustomers().get(0);
        ObjectMapper enteredJson = new ObjectMapper();
        String enteredCustomer = enteredJson.writeValueAsString(customer);

        // stub - given
        when(customerService.create(Mockito.any(CustomerDTO.class))).thenReturn(customer);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/customer/create")
                .accept(MediaType.APPLICATION_JSON)
                .content(enteredCustomer)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String createdCustomer = response.getContentAsString();

        //then
        assertEquals(HttpStatus.CREATED.value(),response.getStatus());
        assertThat(createdCustomer).isEqualTo(enteredCustomer);

    }

    @Test
    void deleteCustomer() throws Exception {
        // init
        willDoNothing().given(customerService).delete("24506231362");

        // stub- given
        ResultActions perform = mvc.perform(delete("/customer/delete/24506231362"));

        // then
        perform.andExpect(status().isOk()).andDo(print());

    }

    @Test
    void updateCustomer() throws Exception {
        // init
        CustomerDTO updateCustomerReqDTO = new CustomerDTO("24506231362","Ahmet","Birinci",7800.00,"+905395215863",950,25,null);
        Customer customer = customerMapper.toEntity(updateCustomerReqDTO);

        //stub - given
        when(customerService.updateCustomer(any(),any())).thenReturn(customer);
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String requestCustomerStr = objectWriter.writeValueAsString(updateCustomerReqDTO);

        //then
        MockHttpServletResponse response = mvc.perform(put("/customer/update/24506231362")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestCustomerStr))
                .andDo(print())
                .andReturn().getResponse();

        response.setContentType("application/json;charset=UTF-8");

        //validate
        assertEquals(response.getStatus(), HttpStatus.OK.value());
    }

    @Test
    void updateCustomerCredit() throws Exception {

        // init
        CustomerDTO updateCustomerReqDTO = new CustomerDTO("24506231362","Ahmet","Birinci",7800.00,"+905395215863",950,25,null);
        Customer customer = customerMapper.toEntity(updateCustomerReqDTO);

        //stub - given
        when(customerService.updateCustomerCreditScore(any(),any())).thenReturn(customer);
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String requestCustomerStr = objectWriter.writeValueAsString(updateCustomerReqDTO);

        //then
        MockHttpServletResponse response = mvc.perform(put("/customer/creditupdate/24506231362")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestCustomerStr))
                .andDo(print())
                .andReturn().getResponse();

        response.setContentType("application/json;charset=UTF-8");

        //validate
        assertEquals(response.getStatus(), HttpStatus.OK.value());
    }

    private List<Customer> getSampleTestCustomers() {
        List<Customer> expCustomerList = new ArrayList<>();
        Customer customer1 = new Customer("24506231362","Ahmet","Birinci",25,"+905369378309",7500.00,550,null);
        Customer customer2 = new Customer("24506231363","Mehmet","İkinci",22,"+905369378310",5500.00,720,null);
        Customer customer3 = new Customer("24506231364","Hasan","Üçüncü",35,"+905369378308",1200.00,195,null);
        expCustomerList.add(customer1);
        expCustomerList.add(customer2);
        expCustomerList.add(customer3);
        return expCustomerList;
    }
}