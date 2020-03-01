package atdd.station.controller;

import atdd.path.application.dto.favorite.FavoriteCreateRequestView;
import atdd.path.application.dto.favorite.FavoriteCreateResponseView;
import atdd.path.application.dto.favorite.FavoriteListResponseView;
import atdd.path.domain.User;
import atdd.path.security.LoginUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {
    private atdd.path.application.FavoriteService favoriteService;

    public FavoriteController(atdd.path.application.FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping("")
    public ResponseEntity create(@LoginUser User user, @RequestBody FavoriteCreateRequestView favorite) {
        FavoriteCreateResponseView savedFavorite = favoriteService.save(user, favorite);
        return ResponseEntity.created(URI.create("/favorites/" + savedFavorite.getId())).body(savedFavorite);
    }

    @GetMapping("")
    public ResponseEntity findByUser(@LoginUser User user, @RequestParam String type) {
        FavoriteListResponseView favorites = favoriteService.findByUser(user, type);
        return ResponseEntity.ok(favorites);
    }

    @DeleteMapping("{itemId}")
    public ResponseEntity deleteItem(@LoginUser User user, @PathVariable Long itemId) {
        favoriteService.deleteItem(user, itemId);
        return ResponseEntity.ok().build();
    }
}
