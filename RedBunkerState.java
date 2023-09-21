package invaders.entities;

public class RedBunkerState implements BunkerState {
    @Override
    public String changecolor(double width) {
        String redImage;
        if (width == 100){
            redImage = "/Users/apple/Desktop/2023_S2/soft2201/A2/A2_code/SpaceInvaders/src/main/resources/RedBunker.jpg";
        } else redImage = "/Users/apple/Desktop/2023_S2/soft2201/A2/A2_code/SpaceInvaders/src/main/resources/RedBunker_150.jpg";
        return redImage;
    }
}
