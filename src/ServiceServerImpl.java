import java.rmi.*;
import java.util.*;
import java.rmi.server.*;

public class ServiceServerImpl extends UnicastRemoteObject implements ServiceServer {
    HashMap serviceList;

    public ServiceServerImpl() throws RemoteException{
        setUpService();
    }

    public void setUpService(){
        serviceList = new HashMap();
        serviceList.put("Dice Rolling Service", new DiceService());
        serviceList.put("Day of the week service", new DayofTheWeek());
        serviceList.put("Visual Music Service", new MiniMusicService());
    }

    public Object[] getServiceList(){
        System.out.println("in remote");
        return serviceList.keySet().toArray();
    }

    public Service getService(Object serviceKey) throws RemoteException{
        Service theService = (Service) serviceList.get(serviceKey);
        return theService;
    }

    public static void main(String[] args) {
        try{
            Naming.rebind("ServiceServer", new ServiceServerImpl());
        }catch (Exception ex){
            System.out.println("Remote service is running");
        }
    }


}
