package invaders.entities;


public class GreenBunkerState implements BunkerState {
    @Override
    public String changecolor(double width) {
        String greenImage;
        if (width == 100){
            greenImage = "/Users/apple/Desktop/2023_S2/soft2201/A2/A2_code/SpaceInvaders/src/main/resources/greenBunker.jpg";
        } else greenImage = "/Users/apple/Desktop/2023_S2/soft2201/A2/A2_code/SpaceInvaders/src/main/resources/greenBunker_150.jpg";
        return greenImage;
    }
}
