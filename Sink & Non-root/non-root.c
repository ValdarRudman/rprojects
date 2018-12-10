/**
 * \file
 *         non-root for Assesment
 * \author
 *         Valdar Rudman <valdar.rudman@mycit.ie>
 */

#include "contiki.h"
#include "net/rime/rime.h"
#include <stdio.h>

/*---------------------------------------------------------------------------*/

//Declare the process
PROCESS(non_root_process, "Non-Root");

//Make the process start when the module is loaded
AUTOSTART_PROCESSES(&non_root_process);

/*---------------------------------------------------------------------------*/

//Unicast connection
static struct unicast_conn uc;
//Broadcast connection
static struct broadcast_conn broadcast;
//Metric value and sequence value.
static int metric = -1;
static int seq = -1;
//Address of parent
static linkaddr_t *parent;
//check to see if connected.
static int connected = 0;
//check to see if recieved first beacon
static int check = 0;

//Struct containg the readings from the sensors. This will be sent to the collector
struct beacon{
	
	int metric;
	int seq;
	int resetSeq;

};
/*---------------------------------------------------------------------------*/

//If broadcast signal received executes the following code
static void
broadcast_recv(struct broadcast_conn *c, const linkaddr_t *from)
{
	
	struct beacon *msg;

	msg = packetbuf_dataptr();

	int resetSeq = 0;

	//Checks to see if sequence has been reset. It also checks to see if the seq > 0
	//It does this so broadcasts from nodes that arent the sink cause a loop with each other
	if(msg->resetSeq == 1 && seq > 0){

		seq = -1;
		resetSeq = 1;
		
	}

	int newMetric = msg->metric;
	int newSeq = msg->seq;
	
	// check to see if new sequence 
	if(seq < newSeq){ // seq < newSeq
		
		metric = newMetric + 1;
		seq = newSeq;
		linkaddr_copy(parent, from);

		printf("New BEACON PARENT for non-root: BEACON PARENT - %d.%d\n\n", from->u8[0], from->u8[1]);

		connected = 1;
		check = 1;

		packetbuf_clear();

		//sends new sequence and metric
		msg = (struct values *) packetbuf_dataptr(); 
		packetbuf_set_datalen(sizeof(struct beacon));

		msg->metric = metric;
		msg->seq = seq;
		msg->resetSeq = resetSeq;

		broadcast_send(&broadcast);
		

	}// if same sequence, checks to see if metric is larger then receiving metric
	else if(metric > newMetric + 1){

		metric = newMetric + 1;
		linkaddr_copy(parent, from);

		printf("New METRIC PARENT for non-root: Metric PARENT - %d.%d\n\n", from->u8[0], from->u8[1]);

		connected = 1;
		check = 1;

		packetbuf_clear();

		msg = (struct beacon *) packetbuf_dataptr(); 
		packetbuf_set_datalen(sizeof(struct beacon));

		msg->metric = metric;
		msg->seq = seq;
		msg->resetSeq = resetSeq;

		broadcast_send(&broadcast);

	}

}
//Calls broadcast_recv if a broadcast is recieved, executing whats in the broadcast_recv method
static const struct broadcast_callbacks broadcast_call = {broadcast_recv};
/*---------------------------------------------------------------------------*/

//Code that is executed if a unicast signal is received.
static void
recv_uc(struct unicast_conn *c, const linkaddr_t *from)
{

}
//Execute recv_uc if unicast signal is received
static const struct unicast_callbacks unicast_callbacks = {recv_uc};
/*---------------------------------------------------------------------------*/

//Define the process code
PROCESS_THREAD(non_root_process, ev, data)
{
	
	// etimer, used to take tempature and light readings as set times
	static struct etimer etimer;
	
	//On process exit, close both broadcast and unicast connections
	PROCESS_EXITHANDLER(broadcast_close(&broadcast);)
	PROCESS_EXITHANDLER(unicast_close(&uc);)

	PROCESS_BEGIN();
	
	//Open connections
	broadcast_open(&broadcast, 110, &broadcast_call);

	while(1){
		
		//Set time for etimer
		etimer_set(&etimer, 3333);
		
		//Wait till timer up to take tempature and light readings
		PROCESS_WAIT_UNTIL(etimer_expired(&etimer));

		//printf("3333 ETIMER\n\n");

		//check to see not connected and has received at leats one beacon
		if(connected == 0 && check == 1){

			// if not connected resets metric and broadcasts a not connected message
			// to see if any nodes in range to connect to
			metric = -1;

			printf("NOT CONNECTED\n\n");

		}
		
		//set connected to 0, this will be set back to 1 if an ok is received from another node
		connected = 0;

	}

	PROCESS_END();
}
/*---------------------------------------------------------------------------*/