package F28DA_CW2;

import java.util.Set;

public interface IAirportPartC {
	
	Set<Airport> getDicrectlyConnected();

	void setDicrectlyConnected(Set<Airport> dicrectlyConnected);

	void setDicrectlyConnectedOrder(int order);

	int getDirectlyConnectedOrder();


}
