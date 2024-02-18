package com.melodymarket.presentation.theater.controller;

import com.melodymarket.application.theater.dto.TheaterDto;
import com.melodymarket.application.theater.service.ManageTheaterService;
import com.melodymarket.application.theater.service.ManageTheaterServiceImpl;
import com.melodymarket.common.dto.ResponseDto;
import com.melodymarket.presentation.theater.dto.TheaterResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/theater")
public class ManageTheaterController {

    ManageTheaterService manageTheaterService;

    public ManageTheaterController(ManageTheaterServiceImpl manageTheaterServiceImpl) {
        this.manageTheaterService = manageTheaterServiceImpl;
    }

    @PostMapping("/add")
    public ResponseEntity<ResponseDto<TheaterResponseDto>> addTheater(@RequestBody @Validated TheaterDto theaterDto) {

        return new ResponseEntity<>(ResponseDto.of(HttpStatus.OK,
                "공연장 등록이 완료되었습니다.",
                manageTheaterService.saveTheater(theaterDto)), HttpStatus.OK);
    }
}