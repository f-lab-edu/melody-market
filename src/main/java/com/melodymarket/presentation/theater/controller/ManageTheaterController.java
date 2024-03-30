package com.melodymarket.presentation.theater.controller;

import com.melodymarket.application.theater.dto.TheaterDto;
import com.melodymarket.application.theater.service.ManageTheaterService;
import com.melodymarket.application.theater.service.ManageTheaterServiceImpl;
import com.melodymarket.common.dto.ResponseDto;
import com.melodymarket.infrastructure.security.MelodyUserDetails;
import com.melodymarket.presentation.theater.dto.TheaterResponseDto;
import com.melodymarket.presentation.theater.dto.TheaterRoomResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/list")
    public ResponseDto<List<TheaterResponseDto>> getTheaterList(@RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
                                                                @RequestParam(required = false, defaultValue = "name", value = "criteria") String criteria,
                                                                @AuthenticationPrincipal MelodyUserDetails userDetails) {
        log.info("user Id ={}", userDetails.getId());
        return ResponseDto.of(HttpStatus.OK,
                "공연장 리스트 조회 성공",
                manageTheaterService.getTheaterList(userDetails.getId(), pageNo, criteria));
    }

    @GetMapping("/list/{theater-id}/rooms")
    public ResponseDto<List<TheaterRoomResponseDto>> getDetailsTheaterRoom(@PathVariable("theater-id") Long theaterId,
                                                                           @RequestParam(required = false, defaultValue = "0", value = "page") int pageNo,
                                                                           @RequestParam(required = false, defaultValue = "name", value = "criteria") String criteria,
                                                                           @AuthenticationPrincipal MelodyUserDetails userDetails) {
        log.info("user Id ={}", userDetails.getId());
        return ResponseDto.of(HttpStatus.OK,
                "공연장 홀 정보 리스트 조회 성공",
                manageTheaterService.getTheaterRoomList(userDetails.getId(), theaterId, pageNo, criteria));
    }
}
