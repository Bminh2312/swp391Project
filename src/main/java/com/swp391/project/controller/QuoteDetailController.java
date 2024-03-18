package com.swp391.project.controller;

import com.swp391.project.dto.QuoteDetailDTO;
import com.swp391.project.payload.request.QuoteDetailForProductRequest;
import com.swp391.project.payload.request.QuoteDetailForRawRequest;
import com.swp391.project.payload.response.BaseResponse;
import com.swp391.project.service.impl.QuoteDetailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quoteDetail")
@CrossOrigin(origins = {"http://localhost:8082", "https://furniture-quote.azurewebsites.net",
        "https://eclectic-belekoy-79d097.netlify.app/", "http://localhost:3000"}, allowCredentials = "true")
public class QuoteDetailController {

    @Autowired
    private QuoteDetailServiceImp quoteDetailServiceImp;

    @PostMapping(value = "/product/createQuoteDetail")
    public ResponseEntity<?> createQuoteDetailForProduct(@RequestBody QuoteDetailForProductRequest quoteDetailRequest){
        BaseResponse baseResponse = new BaseResponse();
        boolean check = quoteDetailServiceImp.createQuoteForProduct(quoteDetailRequest);
        if(check){
            baseResponse.setStatusCode(201);
            baseResponse.setMesssage("Create Successfull");
            baseResponse.setData("True");
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }
        baseResponse.setStatusCode(200);
        baseResponse.setMesssage("Create Failed");
        baseResponse.setData("False");
        return new ResponseEntity<>(baseResponse,HttpStatus.NOT_FOUND);

    }

    @PostMapping(value = "/rawMaterial/createQuoteDetail")
    public ResponseEntity<?> createQuoteDetailforRawMaterial(@RequestBody QuoteDetailForRawRequest quoteDetailRequest){
        BaseResponse baseResponse = new BaseResponse();
        boolean check = quoteDetailServiceImp.createQuoteForRaw(quoteDetailRequest);
        if(check){
            baseResponse.setStatusCode(201);
            baseResponse.setMesssage("Create Successfull");
            baseResponse.setData("True");
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }
        baseResponse.setStatusCode(200);
        baseResponse.setMesssage("Create Failed");
        baseResponse.setData("False");
        return new ResponseEntity<>(baseResponse,HttpStatus.NOT_FOUND);

    }

    @PutMapping(value = "/product/updateQuoteDetail")
    public ResponseEntity<?> updateQuoteDetailForProduct(@RequestParam int quoteDetailId, @RequestParam(required = false) int productId, @RequestParam(name = "quantity", defaultValue = "0") int quantity, @RequestParam String note, double price){
        boolean check = quoteDetailServiceImp.updateQuoteForProduct(quoteDetailId,productId,note,price,quantity);
        BaseResponse baseResponse = new BaseResponse();
        if(check){
            baseResponse.setStatusCode(200);
            baseResponse.setMesssage("Update Successfull");
            baseResponse.setData("True");
            return new ResponseEntity<>(baseResponse,HttpStatus.OK);
        }
        baseResponse.setStatusCode(400);
        baseResponse.setMesssage("Update Failed");
        baseResponse.setData("False");
        return new ResponseEntity<>(baseResponse,HttpStatus.NOT_FOUND);

    }

//    @PutMapping(value = "/product/updateQuoteDetailByNoteForStaff")
//    public ResponseEntity<?> updateQuoteDetailByNote(@RequestParam int quoteDetailId, @RequestParam(required = false) int productId, @RequestParam double price, @RequestParam int quantityChange){
//        boolean check = quoteDetailServiceImp.updateQuoteForProductByNoteForStaff(quoteDetailId,productId,price,quantityChange);
//        BaseResponse baseResponse = new BaseResponse();
//        if(check){
//            baseResponse.setStatusCode(200);
//            baseResponse.setMesssage("Update Successfull");
//            baseResponse.setData("True");
//            return new ResponseEntity<>(baseResponse,HttpStatus.OK);
//        }
//        baseResponse.setStatusCode(400);
//        baseResponse.setMesssage("Update Failed");
//        baseResponse.setData("False");
//        return new ResponseEntity<>(baseResponse,HttpStatus.NOT_FOUND);
//
//    }

//    @PutMapping(value = "/product/updateQuoteDetailByNoteForUser")
//    public ResponseEntity<?> updateQuoteDetailByNoteForUser(@RequestParam int quoteDetailId, @RequestParam(required = false) int productId, @RequestParam double price, @RequestParam int quantityChange, @RequestParam String note){
//        int check = quoteDetailServiceImp.updateQuoteForProductByNoteForUser(quoteDetailId,quantityChange,price,note);
//        BaseResponse baseResponse = new BaseResponse();
//        if(check != 0){
//            baseResponse.setStatusCode(200);
//            baseResponse.setMesssage("Update Successfull");
//            baseResponse.setData(check);
//            return new ResponseEntity<>(baseResponse,HttpStatus.OK);
//        }
//        baseResponse.setStatusCode(400);
//        baseResponse.setMesssage("Update Failed");
//        baseResponse.setData(check);
//        return new ResponseEntity<>(baseResponse,HttpStatus.NOT_FOUND);
//
//    }


    @PutMapping(value = "/rawMaterial/updateQuoteDetail")
    public ResponseEntity<?> updateQuoteDetailForRaw(@RequestParam int quoteDetailId, @RequestParam(required = false) int rawMaterialId, @RequestParam double area ){
        boolean check = quoteDetailServiceImp.updateQuoteForRaw(quoteDetailId,rawMaterialId,area);
        BaseResponse baseResponse = new BaseResponse();
        if(check){
            baseResponse.setStatusCode(200);
            baseResponse.setMesssage("Update Successfull");
            baseResponse.setData("True");
            return new ResponseEntity<>(baseResponse,HttpStatus.OK);
        }
        baseResponse.setStatusCode(400);
        baseResponse.setMesssage("Update Failed");
        baseResponse.setData("False");
        return new ResponseEntity<>(baseResponse,HttpStatus.NOT_FOUND);

    }

    @GetMapping("/getQuoteDetailById")
    public ResponseEntity<?> getQuoteDetailById(@RequestParam int id){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMesssage("SucessFull");
        baseResponse.setStatusCode(200);
        QuoteDetailDTO quoteDetailDTO = quoteDetailServiceImp.findById(id);
        if(quoteDetailDTO == null){
            baseResponse.setMesssage("Not Found");
            baseResponse.setStatusCode(400);
            baseResponse.setData(null);
            return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
        }else{
            baseResponse.setData(quoteDetailDTO);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }


    }

    @GetMapping("/getAllQuoteDetail")
    public ResponseEntity<?> getAllQuoteDetailById(){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMesssage("SucessFull");
        baseResponse.setStatusCode(200);
        List<QuoteDetailDTO> quoteDetailDTO = quoteDetailServiceImp.findAll();
        if(quoteDetailDTO == null){
            baseResponse.setMesssage("Not Found");
            baseResponse.setStatusCode(200);
            baseResponse.setData(null);
            return new ResponseEntity<>(baseResponse, HttpStatus.NOT_FOUND);
        }else{
            baseResponse.setData(quoteDetailDTO);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }


    }

}
