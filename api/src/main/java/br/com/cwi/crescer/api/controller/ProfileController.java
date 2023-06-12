package br.com.cwi.crescer.api.controller;

import br.com.cwi.crescer.api.controller.request.ProfileEditedRequest;
import br.com.cwi.crescer.api.controller.response.*;
import br.com.cwi.crescer.api.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private DetailProfileService detailProfileService;

    @Autowired
    private ListFriendshipService listFriendshipService;

    @Autowired
    private UserStatusService userStatusService;

    @Autowired
    private UserAchievementService userAchievementService;

    @Autowired
    private SearchUsuarioService searchUsuarioService;

    @Autowired
    private ProfileEditService profileEditService;

    @Autowired
    private ProfileIconService profileIconService;

    @GetMapping()
    @ResponseStatus(OK)
    public ProfileResponse profileDetails(){
        return detailProfileService.detail();
    }

    @GetMapping("/friends")
    @ResponseStatus(OK)
    public Page<FriendshipResponse> listFriendships(Pageable pageable){
        return listFriendshipService.get(pageable);
    }

    @GetMapping("/my_friend_requests")
    @ResponseStatus(OK)
    public List<FriendshipResponse> listMyFriendRequests(){
        return listFriendshipService.getMyRequests();
    }

    @GetMapping("/friend_requests")
    @ResponseStatus(OK)
    public List<FriendshipResponse> listFriendRequests(){
        return listFriendshipService.getRequests();
    }

    @GetMapping("/status")
    @ResponseStatus(OK)
    public UserStatusResponse userStatus(){
        return userStatusService.get();
    }

    @GetMapping("/achievements")
    @ResponseStatus(OK)
    public List<UserAchievementResponse> userAchievement(){
        return userAchievementService.get();
    }

    @GetMapping("/search")
    @ResponseStatus(OK)
    public Page<ProfileSummaryResponse> searchUsersByNameOrEmail(@RequestParam String text, Pageable pageable){
        return searchUsuarioService.byNameOrEmailContains(text, pageable);
    }

    @PutMapping("/edit")
    @ResponseStatus(CREATED)
    public ProfileEditedResponse editProfile(@Valid @RequestBody ProfileEditedRequest request) {
        return profileEditService.edit(request);
    }

    @GetMapping("/icons")
    @ResponseStatus(OK)
    public List<IconResponse> listIcons() {
        return profileIconService.list();
    }
}
