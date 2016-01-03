package com.cakebandit;

public enum GameState {

    IN_LOBBY(true), PRE_GAME(false), IN_GAME(true), POST_GAME(false), RESET(false);
    
    private boolean canJoin;
    private static GameState currentState;

    GameState(boolean canJoin) {
        this.canJoin = canJoin;
    }

    public boolean canJoin() {
        return canJoin;
    }

    public static void setState(GameState state) {
        GameState.currentState = state;
    }

    public static boolean isState(GameState state) {
        return GameState.currentState == state;
    }

    public static GameState getState() {
        return currentState;
    }
}
