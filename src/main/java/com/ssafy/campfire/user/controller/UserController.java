package com.ssafy.campfire.user.controller;

import com.ssafy.campfire.global.jwt.service.JwtService;
import com.ssafy.campfire.global.oauth2.PrincipalDetails;
import com.ssafy.campfire.user.domain.User;
import com.ssafy.campfire.user.dto.request.UserPermissionRequest;
import com.ssafy.campfire.user.dto.request.UserUpdateRequest;
import com.ssafy.campfire.user.dto.response.UserConfirmResponse;
import com.ssafy.campfire.user.dto.response.UserReadResponse;
import com.ssafy.campfire.user.dto.response.UserUpdateResponse;
import com.ssafy.campfire.user.service.UserService;
import com.ssafy.campfire.utils.dto.response.BaseResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/jwt-test")
    public String jwtTest() {
        return "jwtTest success";
    }

    @ApiOperation(value ="개인 정보 조회")
    @GetMapping("")
    public BaseResponseDto<UserReadResponse> userInfo(@AuthenticationPrincipal PrincipalDetails user) {
        return BaseResponseDto.ok(userService.read(user.getId()));
    }
    @ApiOperation(value = "개인 정보 수정")
    @PutMapping("")
    public BaseResponseDto<UserUpdateResponse> update(@AuthenticationPrincipal PrincipalDetails user, @RequestBody @Valid UserUpdateRequest request){
        // 현재 postman으로 로그인이 안돼서 임의로 고정값을 뒀습니다.
        //return BaseResponseDto.ok(userService.update(user.getId(), request));
        return BaseResponseDto.ok(userService.update(1L, request));
    }

    @ApiOperation("닉네임 중복 검사")
    @PostMapping("/duplication")
    public BaseResponseDto<String> duplicationCheck(@RequestBody User user){
        boolean state = userService.nickanameDuplicationCheck(user.getNickname());
        if(!state){
            return BaseResponseDto.message(user.getNickname(),"이미 사용 중인 닉네임입니다.");
        }
        return BaseResponseDto.message(user.getNickname(), user.getNickname()+"은 사용 가능한 닉네임입니다.");
    }

    @ApiOperation("소속 인증 요청하기")
    @PostMapping("/confirm")
    public BaseResponseDto<Boolean> confirmRequest(@AuthenticationPrincipal PrincipalDetails user){
        // 현재 postman으로 로그인이 안돼서 임의로 고정값을 뒀습니다.
        boolean state = userService.confirmRequest(2L);
        return BaseResponseDto.ok(state);
    }

    @ApiOperation("소속 인증 요청 리스트 받아오기 - 관리자")
    @GetMapping("/admin/permission/list")
    public BaseResponseDto<List<UserConfirmResponse>> permissionList(){
        List<UserConfirmResponse> userConfirmResponseList = userService.needPermissionUserList();
        for (UserConfirmResponse user: userConfirmResponseList
        ) {
            System.out.println(user.toString());
        }
        return BaseResponseDto.ok(userService.needPermissionUserList());
    }

    @ApiOperation("소속 인증 허가 - 관리자")
    @PutMapping("/admin/permission/{userId}")
    public BaseResponseDto<User> permission(@PathVariable Long userId, @RequestBody UserPermissionRequest request){
        System.out.println(request.bootcampId());
        return BaseResponseDto.ok(userService.permission(userId, request.bootcampId()));
    }

    @ApiOperation("소속 인증 반려 - 관리자")
    @PutMapping("/admin/reject/{userId}")
    public BaseResponseDto<String> notPermission(@PathVariable Long userId){
        return BaseResponseDto.ok(userService.notPermission(userId));
    }
}
