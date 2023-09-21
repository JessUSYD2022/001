package invaders.entities;

public class YellowBunkerState implements BunkerState {
    @Override
    public String changecolor(double wdith) {
        String yellowImage;
        if (wdith == 100){
            yellowImage = "/Users/apple/Desktop/2023_S2/soft2201/A2/A2_code/SpaceInvaders/src/main/resources/YellowBunker.jpg";
        } else yellowImage = "/Users/apple/Desktop/2023_S2/soft2201/A2/A2_code/SpaceInvaders/src/main/resources/YellowBunker_150.jpg";
        return yellowImage;
    }
}
