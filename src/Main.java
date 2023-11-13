import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class Main {

    public static void main(String[] args) {
        try {
            // Getting an instance of Runtime for JADE
            Runtime runtime = Runtime.instance();

            // Setting up a profile for creating the main container
            Profile profile = new ProfileImpl();
            profile.setParameter(Profile.MAIN_HOST, "localhost");
            profile.setParameter(Profile.GUI, "true");

            // Creating the main container
            AgentContainer mainContainer = runtime.createMainContainer(profile);

            // Setting up a profile for creating an additional container for sellers
            Profile sellerProfile = new ProfileImpl();
            sellerProfile.setParameter(Profile.MAIN_HOST, "localhost");
            sellerProfile.setParameter(Profile.CONTAINER_NAME, "Sellers-Container");
            AgentContainer sellerContainer = runtime.createAgentContainer(sellerProfile);

            // Setting up a profile for creating an additional container for buyers
            Profile buyerProfile = new ProfileImpl();
            buyerProfile.setParameter(Profile.MAIN_HOST, "localhost");
            buyerProfile.setParameter(Profile.CONTAINER_NAME, "Buyers-Container");
            AgentContainer buyerContainer = runtime.createAgentContainer(buyerProfile);

            // Creating seller agents
            for (int i = 1; i <= 3; i++) {
                String agentName = "Seller" + i;
                AgentController seller = sellerContainer.createNewAgent(agentName, "com.books.BookSellerAgent", null);
                seller.start();
            }

            // Creating a buyer agent
            String buyerName = "Buyer1";
            AgentController buyer = buyerContainer.createNewAgent(buyerName, "com.books.BookBuyerAgent", new Object[]{"Java Book"});
            buyer.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }
}
