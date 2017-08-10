/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kakawait;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author jelena
 */
@RestController
@RequestMapping("/")
public class CompanyController {

    @Autowired
    CompanyRepository companyRepository;
//    @Autowired
//    LoadBalancerClient loadBalancerClient;

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }
//    private final ICheckOutService checkOutService;
//
//    private final LoadBalancerClient loadBalancerClient;
//
//    public CheckOutController(LoadBalancerClient loadBalancerClient, ICheckOutService checkOutService) {
//        this.checkOutService = checkOutService;
//        this.loadBalancerClient = loadBalancerClient;
//    }
//
//    @RequestMapping(value = "/generateItemizedBill", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseDto getPrice(@RequestParam(value = "productIds", required = true) final String productIds,
//            @RequestHeader(value = "authorization", required = false) String authorization) {
//        String[] stringIds = productIds.split(",");
//        long[] ids = Arrays.stream(stringIds).mapToLong(Long::parseLong).toArray();
//        ServiceInstance serviceInstance = loadBalancerClient.choose(ServiceName.PRODUCT_CATALOGUE_SERVICE.getName());
//        URI uri = serviceInstance.getUri();
//        String url = uri.toString() + "/products/searchbyIds?productIds=" + productIds;
//
//        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
//        interceptors.add(new HeaderRequestInterceptor("authorization", authorization));
//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.setInterceptors(interceptors);
//
//        ProductDto[] productDtos = restTemplate.getForObject(url, ProductDto[].class);
//        return checkOutService.getItemizedBills(Arrays.asList(productDtos));
//    }
}
