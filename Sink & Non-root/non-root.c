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
	
	uint16_t metric;
	uint16_t seq;

};


/*---------------------------------------------------------------------------*/

//If broadcast signal received executes the following code
static void
broadcast_recv(struct broadcast_conn *c, const linkaddr_t *from)
{
	//converts information received. This could be incorrect
	char *msg = (char *)packetbuf_dataptr();
	char *nc = "nc";

	//checks to see if info recieved is equal to nc. NC is no connection
	//If equal sends this nodes metric and sequence
	if(strcmp(nc, msg) == 0){

		struct beacon *msg;

		msg = (struct values *) packetbuf_dataptr(); 

		msg->metric = metric;
		msg->seq= seq;

		unicast_send(&uc, from);
		

	}
	else{ // if not equals reads in beacon

		struct beacon *msg;

		msg = packetbuf_dataptr();

		int newMetric = msg->metric;
		int newSeq = msg->seq;
	
		// check to see if new sequence Use != so you can reset sequence in sink
		if(seq != newSeq){ // seq < newSeq
		
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
			msg->seq= seq;

			broadcast_send(&broadcast);

		}// if same sequence, checks to see if metric is larger then receiving metric
		else if(metric > newMetric){

			metric = newMetric + 1;
			linkaddr_copy(parent, from);

			printf("New METRIC PARENT for non-root: Metric PARENT - %d.%d\n\n", from->u8[0], from->u8[1]);

			connected = 1;
			check = 1;

			packetbuf_clear();

			msg = (struct values *) packetbuf_dataptr(); 
			packetbuf_set_datalen(sizeof(struct beacon));

			msg->metric = metric;
			msg->seq= seq;

			broadcast_send(&broadcast);

		}

	}

}
//Calls broadcast_recv if a broadcast is recieved, executing whats in the broadcast_recv method
static const struct broadcast_callbacks broadcast_call = {broadcast_recv};

/*---------------------------------------------------------------------------*/

//Code that is executed if a unicast signal is received.
static void
recv_uc(struct unicast_conn *c, const linkaddr_t *from)
{

	char *msg = (char *)packetbuf_dataptr();

	char *hello = "hello";
	char *ok = "ok";
	
	//check to see if node is testing connection or sending an ok to say connected
	if(strcmp(hello, msg) == 0){

		if(metric != -1){

			printf("TEST FROM - %d.%d\n\n", from->u8[0], from->u8[1]);

			packetbuf_copyfrom("ok", 3);

			unicast_send(&uc, from);

		}

	}
	else if(strcmp(ok, msg) == 0){

		connected = 1;

		printf("TEST OK from PARENT - %d.%d\n\n", from->u8[0], from->u8[1]);

	}
	else
	{
		// if message is beacon 
		struct beacon *msg;

		msg = packetbuf_dataptr();

		int newMetric = msg->metric;
		int newSeq = msg->seq;

		if(metric > newMetric){
		
			metric = newMetric + 1;
			seq = newSeq;
			linkaddr_copy(parent, from);

			printf("New BEACON PARENT for non-root: BEACON PARENT - %d.%d\n\n", from->u8[0], from->u8[1]);

			connected = 1;
		}

	}

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
	unicast_open(&uc, 111, &unicast_callbacks);

	while(1){
		
		//Set time for etimer
		etimer_set(&etimer, 4000);
		
		//Wait till timer up to take tempature and light readings
		PROCESS_WAIT_UNTIL(etimer_expired(&etimer));

		//check to see not connected and has received at leats one beacon
		if(connected == 0 && check == 1){

			// if not connected resets metric and broadcasts a not connected message
			// to see if any nodes in range to connect to
			metric = -1;

			packetbuf_copyfrom("nc", 3);

			printf("NOT CONNECTED\n\n");

			broadcast_send(&broadcast);

		}
		
		//set connected to 0, this will be set back to 1 if an ok is received from another node
		connected = 0;

		packetbuf_copyfrom("hello", 6);

		unicast_send(&uc, parent);

	}

	PROCESS_END();
}

/*---------------------------------------------------------------------------*/