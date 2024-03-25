package com.melodymarket.presentation.theater.controller;

import com.melodymarket.application.theater.dto.TheaterDto;
import com.melodymarket.application.theater.service.ManageTheaterService;
import com.melodymarket.application.theater.service.ManageTheaterServiceImpl;
import com.melodymarket.common.dto.ResponseDto;
import com.melodymarket.infrastructure.security.MelodyUserDetails;
import com.melodymarket.presentation.theater.dto.TheaterResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseDto<TheaterResponseDto> addTheater(@RequestBody @Validated TheaterDto theaterDto,
                                                      @AuthenticationPrincipal MelodyUserDetails melodyUserDetails) {

        return ResponseDto.of(HttpStatus.OK,
                "공연장 등록이 완료되었습니다.",
                manageTheaterService.saveTheater(theaterDto, melodyUserDetails.getId()));
    }
}
