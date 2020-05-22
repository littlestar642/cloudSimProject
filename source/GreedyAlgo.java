/*
 * Title:        CloudSim Toolkit
 * Description:  CloudSim (Cloud Simulation) Toolkit for Modeling and Simulation
 *               of Clouds
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2009, The University of Melbourne, Australia
 */

package source;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.PriorityBlockingQueue;

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
import org.cloudbus.cloudsim.VmSchedulerSpaceShared;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

/**
 * A simple example showing how to create two datacenters with one host each and
 * run two cloudlets on them.
 */

public class GreedyAlgo {

	/** The cloudlet list. */
	private static List<Cloudlet> cloudletList;

	/** The vmlist. */
	private static List<Vm> vmlist;

	private static List<JobVector> jobList;
	private static ArrayList<Cloudlet> timeTypeCloudletList = new ArrayList<Cloudlet>();
	private static ArrayList<Cloudlet> bwTypeCloudletList = new ArrayList<Cloudlet>();
	private static HashMap<Integer, Double> tpMap = new HashMap<>();
	private static HashMap<Integer, Double> etpMap = new HashMap<>();
	private static ArrayList<JobVector> timeTypeVectorList = new ArrayList<>();
	private static ArrayList<JobVector> bwTypeVectorList = new ArrayList<>();
	private static HashMap<Integer, Double> bpMap = new HashMap<>();
	private static HashMap<Integer, Double> ebpMap = new HashMap<>();



	/**
	 * Creates main() to run this example
	 */
	public static void main(String[] args) {

		// UserVector usr=new UserVector(1, 2, 1, 2, 1, 3);
		// Log.printLine(usr);

		Log.printLine("Starting greedy wala example");

		try {
			// First step: Initialize the CloudSim package. It should be called
			// before creating any entities.

			createCloudsimInstance();
			

			// Second step: Create Datacenters
			// Datacenters are the resource providers in CloudSim. We need at list one of
			// them to run a CloudSim simulation

			@SuppressWarnings("unused")
			Datacenter datacenter0 = createDatacenter("Datacenter_0");

			// Third step: Create Broker
			DatacenterBroker broker = createBroker();
			int brokerId = broker.getId();

			// Fourth step: Create one virtual machine
			vmlist = new ArrayList<Vm>();

			// Create the JobList array
			jobList = new ArrayList<JobVector>();

			// VM description
			int vmid = 0;
			int mips = 300;
			long size = 1000; // image size (MB)
			int ram = 2048; // vm memory (MB)
			long bw = 2000;
			int pesNumber = 4; // number of cpus
			String vmm = "Xen"; // VMM name

			// create five VMs
			Vm vm1 = new Vm(vmid, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());

			vmid++;
			mips = 200;
			ram = 1024;
			bw = 3000;
			pesNumber = 2;
			Vm vm2 = new Vm(vmid, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());

			vmid++;
			mips = 400;
			ram = 1024;
			bw = 1200;
			pesNumber = 2;
			Vm vm3 = new Vm(vmid, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());

			vmid++;
			mips = 350;
			ram = 512;
			bw = 2500;
			pesNumber = 1;
			Vm vm4 = new Vm(vmid, brokerId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerTimeShared());

			// add the VMs to the vmList

			vmlist.add(vm1);
			vmlist.add(vm2);
			vmlist.add(vm3);
			vmlist.add(vm4);

			// submit vm list to the broker
			broker.submitVmList(vmlist);

			cloudletList = new ArrayList<Cloudlet>();

			// Cloudlet properties
			int id = 0;
			long length = 4000;
			long fileSize = 2500;
			long outputSize = 500;
			UtilizationModel utilizationModel = new UtilizationModelFull();

			Cloudlet cloudlet1 = new Cloudlet(id, length, pesNumber, fileSize, outputSize, utilizationModel,
					utilizationModel, utilizationModel);
			cloudlet1.setUserId(brokerId);
			JobVector j1 = new JobVector(1, 0, 0, 400, 0, 0, 0, cloudlet1);

			id++;
			length = 3000;
			fileSize = 2500;
			outputSize = 400;
			Cloudlet cloudlet2 = new Cloudlet(id, length, pesNumber, fileSize, outputSize, utilizationModel,
					utilizationModel, utilizationModel);
			cloudlet2.setUserId(brokerId);
			JobVector j2 = new JobVector(1, 0, 0, 200, 0, 0, 0, cloudlet2);

			id++;
			length = 2000;
			fileSize = 800;
			outputSize = 300;
			Cloudlet cloudlet3 = new Cloudlet(id, length, pesNumber, fileSize, outputSize, utilizationModel,
					utilizationModel, utilizationModel);
			cloudlet3.setUserId(brokerId);
			JobVector j3 = new JobVector(1, 0, 0, 150, 0, 0, 0, cloudlet3);

			id++;
			length = 5000;
			fileSize = 5000;
			outputSize = 2000;
			Cloudlet cloudlet4 = new Cloudlet(id, length, pesNumber, fileSize, outputSize, utilizationModel,
					utilizationModel, utilizationModel);
			cloudlet4.setUserId(brokerId);
			JobVector j4 = new JobVector(1, 0, 0, 500, 0, 0, 0, cloudlet4);

			id++;
			length = 2000;
			fileSize = 800;
			outputSize = 300;
			Cloudlet cloudlet5 = new Cloudlet(id, length, pesNumber, fileSize, outputSize, utilizationModel,
					utilizationModel, utilizationModel);
			cloudlet5.setUserId(brokerId);
			JobVector j5 = new JobVector(2, 0, 0, 0, 2000, 0, 0, cloudlet5);

			id++;
			length = 3000;
			fileSize = 2000;
			outputSize = 400;
			Cloudlet cloudlet6 = new Cloudlet(id, length, pesNumber, fileSize, outputSize, utilizationModel,
					utilizationModel, utilizationModel);
			cloudlet6.setUserId(brokerId);
			JobVector j6 = new JobVector(2, 0, 0, 0, 3000, 0, 0, cloudlet6);

			id++;
			length = 800;
			fileSize = 300;
			outputSize = 300;
			Cloudlet cloudlet7 = new Cloudlet(id, length, pesNumber, fileSize, outputSize, utilizationModel,
					utilizationModel, utilizationModel);
			cloudlet7.setUserId(brokerId);
			JobVector j7 = new JobVector(2, 0, 0, 0, 1200, 0, 0, cloudlet7);

			id++;
			length = 2500;
			fileSize = 1000;
			outputSize = 500;
			Cloudlet cloudlet8 = new Cloudlet(id, length, pesNumber, fileSize, outputSize, utilizationModel,
					utilizationModel, utilizationModel);
			cloudlet8.setUserId(brokerId);
			JobVector j8 = new JobVector(2, 0, 0, 0, 2000, 0, 0, cloudlet8);

			// add the cloudlets to the list
			cloudletList.add(cloudlet1);
			cloudletList.add(cloudlet2);
			cloudletList.add(cloudlet3);
			cloudletList.add(cloudlet4);
			cloudletList.add(cloudlet5);
			cloudletList.add(cloudlet6);
			cloudletList.add(cloudlet7);
			cloudletList.add(cloudlet8);

			// add Job vectors to jobList

			jobList.add(j1);
			jobList.add(j2);
			jobList.add(j3);
			jobList.add(j4);
			jobList.add(j5);
			jobList.add(j6);
			jobList.add(j7);
			jobList.add(j8);


			// separate both the type of jobs
			jobList.forEach(v -> {
				if (v.getClassType() == 1)
					timeTypeVectorList.add(v);
				else
					bwTypeVectorList.add(v);
			});

			// algorithm for time-type processing
			broker = timeTypeJobProcessing(broker,vmlist,1);

			// algorithm for bw-type processing
			broker= bwTypeJobProcessing(broker, vmlist);
			

			// Sixth step: Starts the simulation
			CloudSim.startSimulation();

			// Final step: Print results when simulation is over
			List<Cloudlet> newList = broker.getCloudletReceivedList();

			// add start and end time to job vectors
			newList= setStartAndEndTimeOnJobs(newList);

			// create DS for TPi
			dsForTpiProcessing();
			
			// create DS for ETPi
			dsForEtpiProcessing(newList);
			
			// create DS for JEF time of timeType
			dsForJefTimeType();

			// print the vectorList obtained with priority
			printTimeTypeJobVectorList(timeTypeVectorList);




			// create DS for BPi
			dsForBpiProcessing(vmlist);
			
			// create DS for EBPi
			dsForEbpiProcessing(jobList);
			
			// create DS for JEF time of bwType
			dsForJefBwType();

			// print the vectorList obtained with priority
			printBwTypeJobVectorList(bwTypeVectorList);


			int iterations=10; // change according to requirement
			for(int i=0;i<iterations;i++) {


				Collections.reverse(timeTypeVectorList);

				broker = timeTypeJobProcessing(broker, vmlist, 2);

				newList = broker.getCloudletReceivedList();

				// add start and end time to job vectors
				newList = setStartAndEndTimeOnJobs(newList);

				// create DS for TPi
				dsForTpiProcessing();

				// create DS for ETPi
				dsForEtpiProcessing(newList);

				// create DS for JEF time of timeType
				dsForJefTimeType();

				// print the vectorList obtained with priority
				printTimeTypeJobVectorList(timeTypeVectorList);

			}
			
			// stop cloudsim simulation
			CloudSim.stopSimulation();







			Log.printLine("Greedy Algo Execution finished!");
		} catch (Exception e) {
			e.printStackTrace();
			Log.printLine("The simulation has been terminated due to an unexpected error");
		}
	}


	private static void printBwTypeJobVectorList(ArrayList<JobVector> list) {

		int size = list.size();
		JobVector job;

		String indent = "    ";
		Log.printLine();
		Log.printLine("========== Bandwidth Type Job List ==========");
		Log.printLine("Cloudlet ID" + indent + "STATUS" + indent + "Start Time" + indent + "Finish Time" + indent + "Expected Time" + indent + "Expected Bw"+ indent + "JEF Value" + indent + "priority");

		DecimalFormat dft = new DecimalFormat("###.##");
		for (int i = 0; i < size; i++) {
			job = list.get(i);
			Log.print(indent + job.getCloudlet().getCloudletId() + indent + indent);

			if (job.getCloudlet().getCloudletStatus() == Cloudlet.SUCCESS){
				Log.print("SUCCESS");

				Log.printLine( indent + indent + dft.format(job.getStartTime()) + indent + indent + indent + dft.format(job.getEndTime()) +
						indent + indent + "NA"  + indent + indent + indent + indent + dft.format(job.getExpBw()) +
						indent + indent + indent + dft.format(job.getJval()) + indent + indent +  indent + job.getPriority());
			}
		}
	}

	private static void dsForJefBwType() {
	
		TreeMap<Integer,Double> jefBwMap = new TreeMap<>();

		double theeta = 0.5;
			bwTypeCloudletList.forEach(cl -> {
				jefBwMap.put(cl.getCloudletId(),
						theeta * Math.log(bpMap.get(cl.getVmId()) / ebpMap.get(cl.getCloudletId())));
			});

			Set<Integer> jefBwKs = jefBwMap.keySet();

			for (int key : jefBwKs) {
				bwTypeVectorList.forEach(j -> {
					int ID = j.getCloudlet().getCloudletId();
					if (key == ID) {
						j.setJval(jefBwMap.get(key));
					}
				});
			}

			Collections.sort(bwTypeVectorList, new SortbyJval());
			Priority p=new Priority(1);
			
			bwTypeVectorList.forEach(v->{	
				v.setPriority(p.getPriority());
				p.increment();
			});
	}

	private static void dsForJefTimeType() {

				TreeMap<Integer, Double> jefTimeMap = new TreeMap<>();

			double theeta = 0.5;
			timeTypeCloudletList.forEach(cl -> {
				jefTimeMap.put(cl.getCloudletId(),
						theeta * Math.log(tpMap.get(cl.getCloudletId()) / etpMap.get(cl.getCloudletId())));
			});

			Set<Integer> jefTimeKs = jefTimeMap.keySet();

			for (int key : jefTimeKs) {
				timeTypeVectorList.forEach(j -> {
					int ID = j.getCloudlet().getCloudletId();
					if (key == ID) {
						j.setJval(jefTimeMap.get(key));
					}
				});
			}

			Collections.sort(timeTypeVectorList, new SortbyJval());
			Priority p=new Priority(1);
			
			timeTypeVectorList.forEach(v->{	
				v.setPriority(p.getPriority());
				p.increment();
			});

	}

	private static void dsForEbpiProcessing(List<JobVector> jobList2) {
		ArrayList<Double> ebrList = new ArrayList<>();

			jobList2.forEach(j -> {
				ebrList.add(j.getExpBw());
			});

			double ebrMax = Collections.max(ebrList);
			double ebrMin = Collections.min(ebrList);

			double ebDenom = ebrMax - ebrMin + 1;

			jobList2.forEach(j -> {
				ebpMap.put(j.getCloudlet().getCloudletId(), j.getExpBw() / ebDenom);
			});
	}

	private static void dsForBpiProcessing(List<Vm> vmlist2) {
		ArrayList<Long> brList = new ArrayList<>();

			vmlist2.forEach(vm -> {
				brList.add(vm.getBw());
			});

			double brMax = Collections.max(brList);
			double brMin = Collections.min(brList);

			double bwDenom = brMax - brMin + 1;

			vmlist2.forEach(vm -> {
				bpMap.put(vm.getId(), vm.getBw() / bwDenom);
			});
	}

	private static List<Cloudlet> setStartAndEndTimeOnJobs(List<Cloudlet> newList) {
		newList.forEach(c -> {
			double ID = c.getCloudletId();
			jobList.forEach(j -> {
				if (j.getCloudlet().getCloudletId() == ID) {
					j.setStartTime(c.getExecStartTime());
					j.setEndTime(c.getFinishTime());
				}
			});
		});
		return newList;
	}

	

	private static void dsForEtpiProcessing(List<Cloudlet> newList) {
		HashMap<Integer, Double> etrMap = new HashMap<>();
			ArrayList<Double> etrList = new ArrayList<>();

			newList.forEach(c -> {
				int vmID = c.getVmId();
				int cID = c.getCloudletId();
				vmlist.forEach(vm -> {
					if (vm.getId() == vmID) {
						timeTypeCloudletList.forEach(cl -> {
							if (cl.getCloudletId() == cID) {
								double val = (double) cl.getCloudletLength() / (double) vm.getMips();
								etrMap.put(cID, val);
								etrList.add(val);
							}
						});
					}
				});
			});

			double etrMax = Collections.max(etrList);
			double etrMin = Collections.min(etrList);

			double eDenom = etrMax - etrMin + 1;

			timeTypeCloudletList.forEach(c -> {
				etpMap.put(c.getCloudletId(), etrMap.get(c.getCloudletId()) / eDenom);
			});
	}

	private static void dsForTpiProcessing() {
		ArrayList<Double> trList = new ArrayList<>();

			timeTypeCloudletList.forEach(c -> {
				trList.add(c.getActualCPUTime());
			});

			double trMax = Collections.max(trList);
			double trMin = Collections.min(trList);

			double denom = trMax - trMin + 1;

			timeTypeCloudletList.forEach(c -> {
				tpMap.put(c.getCloudletId(), c.getActualCPUTime() / denom);
			});
	}

	private static DatacenterBroker bwTypeJobProcessing(DatacenterBroker broker,List<Vm> vmlist2) {
				bwTypeVectorList.forEach(t -> {
					bwTypeCloudletList.add(t.getCloudlet());
				});
	
				broker.submitCloudletList(bwTypeCloudletList);
	
				Collections.sort(vmlist, new SortbyBw());
				Collections.sort(bwTypeVectorList, new SortbyExpectationBw());

				for (int i = 0, j = 0; i < bwTypeVectorList.size(); i++, j++) {
					j %= vmlist.size();
					broker.bindCloudletToVm(bwTypeVectorList.get(i).getCloudlet().getCloudletId(), vmlist.get(j).getId());
				}

		return broker ;
	}

	private static DatacenterBroker timeTypeJobProcessing(DatacenterBroker broker,List<Vm> vmlist2,int flag) {

			timeTypeVectorList.forEach(t -> {
				timeTypeCloudletList.add(t.getCloudlet());
			});

			broker.submitCloudletList(timeTypeCloudletList);

			Collections.sort(vmlist, new SortbyMips());
			if(flag==1) {  // for the first iteration, for all other iteration flag=2 and we dont need to sort on basis of expectation time.
				Collections.sort(timeTypeVectorList, new SortbyExpectationTime());
			}
			for (int i = 0, j = 0; i < timeTypeVectorList.size(); i++, j++) {
				j %= vmlist.size();
				broker.bindCloudletToVm(timeTypeVectorList.get(i).getCloudlet().getCloudletId(), vmlist.get(j).getId());
			}
			return broker;

	}

	private static void createCloudsimInstance() {
		int num_user = 1; // number of cloud users
			Calendar calendar = Calendar.getInstance();
			boolean trace_flag = false; // mean trace events

			// Initialize the GridSim library
			CloudSim.init(num_user, calendar, trace_flag);
	}

	private static void printTimeTypeJobVectorList(ArrayList<JobVector> list) {
		int size = list.size();
		JobVector job;

		String indent = "    ";
		Log.printLine();
		Log.printLine("========== Time Type Job List ==========");
		Log.printLine("Cloudlet ID" + indent + "STATUS" + indent + "Start Time" + indent + "Finish Time" + indent + "Expected Time" + indent + "Expected Bw"+ indent + "JEF Value" + indent + "priority");
		int sumOfJefValues=0;
		DecimalFormat dft = new DecimalFormat("###.##");
		for (int i = 0; i < size; i++) {
			job = list.get(i);
			Log.print(indent + job.getCloudlet().getCloudletId() + indent + indent);

			if (job.getCloudlet().getCloudletStatus() == Cloudlet.SUCCESS){
				Log.print("SUCCESS");
				sumOfJefValues+=Math.abs(job.getJval()*10000000);
				Log.printLine( indent + indent + dft.format(job.getStartTime()) + indent + indent + indent + dft.format(job.getEndTime()) +
						indent + indent + dft.format(job.getExpTime()) + indent + indent + indent + indent + "NA" +
						indent + indent + indent + dft.format(job.getJval()) + indent + indent +  indent + job.getPriority());
			}
		}
		Log.printLine(sumOfJefValues);

	}

    

	private static Datacenter createDatacenter(String name){

		// Here are the steps needed to create a PowerDatacenter:
		// 1. We need to create a list to store
		//    our machine
		List<Host> hostList = new ArrayList<Host>();

		// 2. A Machine contains one or more PEs or CPUs/Cores.
		// In this example, it will have only one core.
		List<Pe> peList = new ArrayList<Pe>();

		int mips = 2000;

		// 3. Create PEs and add these into a list.
		peList.add(new Pe(0, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating
		peList.add(new Pe(1, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating
		peList.add(new Pe(2, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating
		peList.add(new Pe(3, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating

		//4. Create Host with its id and list of PEs and add them to the list of machines
		int hostId=0;
		int ram = 1024*4; //host memory (MB)
		long storage = 1000000; //host storage
		int bw = 10000;


		//in this example, the VMAllocatonPolicy in use is SpaceShared. It means that only one VM
		//is allowed to run on each Pe. As each Host has only one Pe, only one VM can run on each Host.
		
		// our quad core first machine
		hostList.add(
    			new Host(
    				hostId,
    				new RamProvisionerSimple(ram),
    				new BwProvisionerSimple(bw),
    				storage,
    				peList,
    				new VmSchedulerSpaceShared(peList)
    			)
			); 
			

		hostId++;
		ram = 1024*2;
		peList = new ArrayList<Pe>();
		peList.add(new Pe(0, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating
		peList.add(new Pe(1, new PeProvisionerSimple(mips)));

		// our dual core second machine
		hostList.add(
    			new Host(
    				hostId,
    				new RamProvisionerSimple(ram),
    				new BwProvisionerSimple(bw),
    				storage,
    				peList,
    				new VmSchedulerSpaceShared(peList)
    			)
			);

		hostId++;
		ram = 1024*2;
		peList = new ArrayList<Pe>();
		peList.add(new Pe(0, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating
		peList.add(new Pe(1, new PeProvisionerSimple(mips)));

		// our dual core third machine
		hostList.add(
				new Host(
					hostId,
					new RamProvisionerSimple(ram),
					new BwProvisionerSimple(bw),
					storage,
					peList,
					new VmSchedulerSpaceShared(peList)
				)
			);


		hostId++;
		ram = 1024*1;
		peList = new ArrayList<Pe>();
		peList.add(new Pe(0, new PeProvisionerSimple(mips))); // need to store Pe id and MIPS Rating

		// our single core fourth machine
		hostList.add(
				new Host(
					hostId,
					new RamProvisionerSimple(ram),
					new BwProvisionerSimple(bw),
					storage,
					peList,
					new VmSchedulerSpaceShared(peList)
				)
			);

		// 5. Create a DatacenterCharacteristics object that stores the
		//    properties of a data center: architecture, OS, list of
		//    Machines, allocation policy: time- or space-shared, time zone
		//    and its price (G$/Pe time unit).
		String arch = "x86";      // system architecture
		String os = "Linux";          // operating system
		String vmm = "Xen";
		double time_zone = 10.0;         // time zone this resource located
		double cost = 3.0;              // the cost of using processing in this resource
		double costPerMem = 0.05;		// the cost of using memory in this resource
		double costPerStorage = 0.001;	// the cost of using storage in this resource
		double costPerBw = 0.0;			// the cost of using bw in this resource
		LinkedList<Storage> storageList = new LinkedList<Storage>();	//we are not adding SAN devices by now

	       DatacenterCharacteristics characteristics = new DatacenterCharacteristics(
	                arch, os, vmm, hostList, time_zone, cost, costPerMem, costPerStorage, costPerBw);


		// 6. Finally, we need to create a PowerDatacenter object.
		Datacenter datacenter = null;
		try {
			datacenter = new Datacenter(name, characteristics, new VmAllocationPolicySimple(hostList), storageList, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return datacenter;
	}

	//We strongly encourage users to develop their own broker policies, to submit vms and cloudlets according
	//to the specific rules of the simulated scenario
	private static DatacenterBroker createBroker(){

		DatacenterBroker broker = null;
		try {
			broker = new DatacenterBroker("Broker");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return broker;
	}

	/**
	 * Prints the Cloudlet objects
	 * @param list  list of Cloudlets
	 */
	private static void printCloudletList(List<Cloudlet> list) {
		int size = list.size();
		Cloudlet cloudlet;

		String indent = "    ";
		Log.printLine();
		Log.printLine("========== OUTPUT ==========");
		Log.printLine("Cloudlet ID" + indent + "STATUS" + indent +
				"Data center ID" + indent + "VM ID" + indent + "Time" + indent + "Start Time" + indent + "Finish Time");

		DecimalFormat dft = new DecimalFormat("###.##");
		for (int i = 0; i < size; i++) {
			cloudlet = list.get(i);
			Log.print(indent + cloudlet.getCloudletId() + indent + indent);

			if (cloudlet.getCloudletStatus() == Cloudlet.SUCCESS){
				Log.print("SUCCESS");

				Log.printLine( indent + indent + cloudlet.getResourceId() + indent + indent + indent + cloudlet.getVmId() +
						indent + indent + dft.format(cloudlet.getActualCPUTime()) + indent + indent + dft.format(cloudlet.getExecStartTime())+
						indent + indent + dft.format(cloudlet.getFinishTime()));
			}
		}

	}
}
