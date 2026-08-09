package santorini.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import santorini.dto.*;
import santorini.service.GameService;

/**
 * REST controller for the Santorini game.
 * Thin controller that delegates to GameService.
 */
@RestController
@RequestMapping("/api")
public class GameRestController {

    private final GameService gameService;

    public GameRestController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Health check endpoint.
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Santorini backend is running");
    }

    /**
     * Creates a new game.
     */
    @PostMapping("/game")
    public ResponseEntity<GameStateDTO> createGame() {
        GameStateDTO state = gameService.createGame();
        return ResponseEntity.ok(state);
    }

    /**
     * Gets the current game state.
     */
    @GetMapping("/game")
    public ResponseEntity<GameStateDTO> getGameState() {
        GameStateDTO state = gameService.getGameState();
        if (state == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(state);
    }

    /**
     * Handles a tile click (select worker, move, or build).
     */
    @PostMapping("/game/click")
    public ResponseEntity<GameStateDTO> clickTile(@RequestBody TileClickRequest request) {
        GameStateDTO state = gameService.clickTile(request.getRow(), request.getCol());
        return ResponseEntity.ok(state);
    }

    /**
     * Resolves a pending choice (god card ability or skip card).
     */
    @PostMapping("/game/choice")
    public ResponseEntity<GameStateDTO> resolveChoice(@RequestBody ChoiceRequest request) {
        GameStateDTO state = gameService.resolveChoice(request.isAccepted());
        return ResponseEntity.ok(state);
    }

    /**
     * Restarts the game.
     */
    @PostMapping("/game/restart")
    public ResponseEntity<GameStateDTO> restartGame() {
        GameStateDTO state = gameService.createGame();
        return ResponseEntity.ok(state);
    }
    /**
     * Ends the current game.
     */
    @PostMapping("/game/end")
    public ResponseEntity<GameStateDTO> endGame() {
        GameStateDTO state = gameService.endGame();
        return ResponseEntity.ok(state);
    }
}
