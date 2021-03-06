package org.cloudbus.cloudsim;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.CloudletSchedulerTimeShared;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelFull;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.VmSchedulerTimeShared;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;


public class Performance extends javax.swing.JFrame{

     
    static Random rand=new Random();
    public static final int r=rand.nextInt(100)+100;
    public static int vmcapacity;
    public static int Rvmcapacity;
    public static int totalvmcapacity;
    public static int  cloudletsize;
    public static int randomcloudletsize;
    public static int totalvm;
    public static int vmr;
    public static int vmrr;
    public static int jobsize[]=new int[1000];
        public static int js[]=new int[1000];

	private static List<Cloudlet> cloudletList;
        private static List<Cloudlet> cloudletList1;

	
	private static List<Vm> vmlist;

	public static List<Vm> createVM(int userId, int vmr, int idShift) {
		LinkedList<Vm> list = new LinkedList<Vm>();

		
		long size = 10000; 
		int ram = 512; 
		int mips = 250;
		long bw = 1000;
		int pesNumber = 1; 
		String vmm = "Xen"; 

		
		Vm[] vm = new Vm[vmr];

		for(int i=0;i<vmr;i++){
			vm[i] = new Vm(idShift + i, userId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
			list.add(vm[i]);  
                         vmcapacity=(int)pesNumber*mips+(int)bw;
                }
                 
                Rvmcapacity=vmcapacity;
                Log.printLine("vm capacity:"+vmcapacity);

		return list; 
	
}
        
        public static List<Vm> createVM1(int userId, int vmr, int idShift) {
		LinkedList<Vm> list = new LinkedList<Vm>();
                //Log.printLine("generated random is"+r);
		long size = 10000; 
		int ram = 512; 
		int mips = 250;
		long bw =1000;
		int pesNumber = 1;
		String vmm = "Xen"; 

		
		Vm[] vm = new Vm[vmr];

		for(int i=0;i<vmr;i++){
			vm[i] = new Vm(idShift + i, userId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());
			list.add(vm[i]);  
                         vmcapacity=(int)pesNumber*mips+(int)bw;
                }
                 
                Rvmcapacity=vmcapacity;
                Log.printLine("vm capacity:"+vmcapacity);

		return list; 
	
}
        
        
        public static List<Cloudlet> createCloudlet1(int userId, int r, int idShift)
        {
		LinkedList<Cloudlet> list = new LinkedList<Cloudlet>();

                   int length[] =new int[1000];
                   int fileSize[] = new int[1000];
                   int outputSize = 300;
                  
		int pesNumber = 1;
                
                for(int j=0; j<r; j++)
                {
                 length[j] = rand.nextInt(); if(length[j]<0) length[j]*=-1 ;
	         fileSize[j]=rand.nextInt();if(fileSize[j]<0) fileSize[j]*=-1;
		 jobsize[j]=(length[j]/1024+fileSize[j]/1024);
                 Log.printLine("cloudlet :"+j+"  length :"+length[j]/1024+"  fileSize :"+fileSize[j]/1024+" jobSize  "+jobsize[j]);
                          
                }
                
                for(int i=0;i<r;i++)
                {
                      randomcloudletsize+= jobsize[i];  
                }
   
                 //Log.print("\n\n Cloudlet overall randomcloudletsize" +randomcloudletsize);
             
           randomcloudletsize=randomcloudletsize/1024;
           Log.print("\n cloudlet overall size   "+ randomcloudletsize);
          do
          {
              Rvmcapacity+=12500;
          }
          while(randomcloudletsize>Rvmcapacity);
          
          
          vmrr=Rvmcapacity/12500;
          totalvm=vmrr-vmr;
           
             Log.printLine("\nvm required" +vmrr);
             Log.printLine("Total capacity:"+12500*vmrr);

	     UtilizationModel utilizationModel = new UtilizationModelFull();

		Cloudlet[] cloudlet1 = new Cloudlet[r];

		for(int i=0;i<r;i++){
			cloudlet1[i] = new Cloudlet(idShift + i, length[i], pesNumber, fileSize[i], outputSize, utilizationModel, utilizationModel, utilizationModel);
			
			cloudlet1[i].setUserId(userId);
			list.add(cloudlet1[i]);
		}
                return list;
	}

        
     	public static List<Cloudlet> createCloudlet(int userId, int cloudlets, int idShift)
{
		LinkedList<Cloudlet> list = new LinkedList<Cloudlet>();

                   int length[] =new int[1000];
                   int fileSize[] = new int[1000];
                   int outputSize = 300;
                  
		int pesNumber = 1;
                 int jobsize[]=new int[1000];
                for(int j=0; j<cloudlets; j++)
                {
                 length[j] = rand.nextInt(); if(length[j]<0) length[j]*=-1 ;
	         fileSize[j]=rand.nextInt();if(fileSize[j]<0) fileSize[j]*=-1;
		 jobsize[j]=(length[j]/1024+fileSize[j]/1024);
                  js[j]=(length[j]/1024+fileSize[j]/1024);
                  js[j]=js[j]/1024;
                 Log.printLine("cloudlet :"+j+"  length :"+length[j]/1024+"  fileSize :"+fileSize[j]/1024+" jobSize  "+jobsize[j]);
                                
                }
                
                for(int i=0;i<cloudlets;i++)
                {
                      cloudletsize+= jobsize[i];  
                }
                
                
           
                // Log.print("\n\n Cloudlet overall size" +cloudletsize);
             
           cloudletsize=cloudletsize/1024;
           Log.print("\n cloudlet overall size   "+ cloudletsize);
          do
          {
              vmcapacity+=12500;
          }
          while(cloudletsize>vmcapacity);
          
          
          vmr=vmcapacity/12500;
           
             Log.printLine("\nvm required" +vmr);
             Log.printLine("Total capacity:"+12500*vmr);

	     UtilizationModel utilizationModel = new UtilizationModelFull();

		Cloudlet[] cloudlet = new Cloudlet[cloudlets];

		for(int i=0;i<cloudlets;i++){
			cloudlet[i] = new Cloudlet(idShift + i, length[i], pesNumber, fileSize[i], outputSize, utilizationModel, utilizationModel, utilizationModel);
			
			cloudlet[i].setUserId(userId);
			list.add(cloudlet[i]);
		}
                
                
                return list;
	}
        
      
        public static void function()
        {
                        Datacenter datacenter1 = createDatacenter("Datacenter_1");
			DatacenterBroker broker = createBroker("Broker_0");

			int brokerId = broker.getId();
                        broker.submitVmList(vmlist);
			broker.submitCloudletList(cloudletList);

                        
                       
        }
        
	public static void main(String[] args) {
		Log.printLine("...................................");

		try {
			
			int num_user = 2;   
			Calendar calendar = Calendar.getInstance();
			boolean trace_flag = false; 

			CloudSim.init(num_user, calendar, trace_flag);
			@SuppressWarnings("unused")
			Datacenter datacenter0 = createDatacenter("Datacenter_0");
			@SuppressWarnings("unused")
			Datacenter datacenter1 = createDatacenter("Datacenter_1");
			DatacenterBroker broker = createBroker("Broker_0");

			int brokerId = broker.getId();
                       
			//cloudletList = createCloudlet(brokerId,15, 0);
                       // vmlist = createVM1(brokerId,vmr, 0); 

			//broker.submitVmList(vmlist);
			//broker.submitCloudletList(cloudletList);
			Runnable monitor = new Runnable() {
				@Override
				public void run() {
					CloudSim.pauseSimulation(200);
					while (true) {
						if (CloudSim.isPaused()) {
							break;
						}
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}

					Log.printLine("\n\n\n" + CloudSim.clock() + ": The simulation is paused for 5 sec \n\n");

					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					DatacenterBroker broker = createBroker("Broker_1");
					int brokerId = broker.getId();

					//Create VMs and Cloudlets and send them to broker
					///creating 5 vms
                                       
					//cloudletList = createCloudlet(brokerId,r, 100); // creating 10 cloudlets
                                        //vmlist = createVM(brokerId,5, 100); 
					broker.submitVmList(vmlist);
					broker.submitCloudletList(cloudletList);

					CloudSim.resumeSimulation();
				}
			};

			new Thread(monitor).start();
			Thread.sleep(1000);

			CloudSim.startSimulation();

			List<Cloudlet> newList = broker.getCloudletReceivedList();

			CloudSim.stopSimulation();

			printCloudletList(newList);

			Log.printLine(" finished!");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Log.printLine("The simulation has been terminated due to an unexpected error");
		}
	}

	private static Datacenter createDatacenter(String name){
		List<Host> hostList = new ArrayList<Host>();

		List<Pe> peList1 = new ArrayList<Pe>();

		int mips = 1000;
		peList1.add(new Pe(0, new PeProvisionerSimple(mips))); 
		peList1.add(new Pe(1, new PeProvisionerSimple(mips)));
		peList1.add(new Pe(2, new PeProvisionerSimple(mips)));
		peList1.add(new Pe(3, new PeProvisionerSimple(mips)));

		List<Pe> peList2 = new ArrayList<Pe>();

		peList2.add(new Pe(0, new PeProvisionerSimple(mips)));
		peList2.add(new Pe(1, new PeProvisionerSimple(mips)));
                
              

		int hostId=0;
		int ram = 16384; 
		long storage = 1000000; 
		int bw = 10000;

		hostList.add(
    			new Host(
    				hostId,
    				new RamProvisionerSimple(ram),
    				new BwProvisionerSimple(bw),
    				storage,
    				peList1,
    				new VmSchedulerTimeShared(peList1)
    			)
    		); 

		hostId++;

		hostList.add(
    			new Host(
    				hostId,
    				new RamProvisionerSimple(ram),
    				new BwProvisionerSimple(bw),
    				storage,
    				peList2,
    				new VmSchedulerTimeShared(peList2)
    			)
    		); 

                
                
                
		String arch = "x86";      
		String os = "Linux";      
		String vmm = "Xen";
		double time_zone = 10.0;  
		double cost = 3.0;        
		double costPerMem = 0.05;		
		double costPerStorage = 0.1;	
		double costPerBw = 0.1;		
		LinkedList<Storage> storageList = new LinkedList<Storage>();	

		DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
                arch, os, vmm, hostList, time_zone, cost, costPerMem, costPerStorage, costPerBw);
		Datacenter datacenter = null;
		try {
			datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return datacenter;
	}

	
	private static DatacenterBroker createBroker(String name){

		DatacenterBroker broker = null;
		try {
			broker = new DatacenterBroker(name);
		} catch (Exception e) {
			e.printStackTrace();
                        
			return null;
		}
		return broker;
	}

        private static void printCloudletList(List<Cloudlet> list) {
		int size = list.size();
		Cloudlet cloudlet;

		String indent = "    ";
		Log.printLine();
		Log.printLine("========== OUTPUT ==========");
		Log.printLine("Cloudlet ID" + indent + "STATUS" + indent +
				"Data center ID" + indent + "VM ID" + indent + indent+ indent + indent );

		DecimalFormat dft = new DecimalFormat("###.##");
		for (int i = 0; i < size; i++) {
			cloudlet = list.get(i);
			Log.print(indent + cloudlet.getCloudletId() + indent + indent);

			if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS){
				Log.print("SUCCESS");

				Log.printLine( indent + indent + cloudlet.getResourceId() + indent + indent + indent + cloudlet.getVmId() +
						indent + indent + indent); 
                        }
		}

	}
}
