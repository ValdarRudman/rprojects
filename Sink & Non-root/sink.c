/**
 * \file
 *         Sink for assesment project
 * \author
 *         Valdar Rudman <valdar.rudman@mycit.ie>
 */

#include "contiki.h"
#include "net/rime/rime.h"
#include <stdio.h>

/*---------------------------------------------------------------------------*/

//Declare the process
PROCESS(broadcast_process, "Broadcast");

//Make the process start when the module is loaded
AUTOSTART_PROCESSES(&broadcast_process);

/*---------------------------------------------------------------------------*/

//Connections
static struct broadcast_conn broadcast;
static struct unicast_conn uc;

//Metric and sequence values
static int metric = 0;
static int seq = -1;

//beacon struct containing current metric and sequence
static struct beacon{

	int metric; //uint16_t
	int seq;

};

/*---------------------------------------------------------------------------*/

//If broadcast signal received executes the following code
static void
broadcast_recv(struct broadcast_conn *c, const linkaddr_t *from)
{
	
	char *msg = (char *)packetbuf_dataptr();
	char *nc = "nc";

	if(strcmp(nc, msg) == 0){

		struct beacon *msg;

		msg = (struct values *) packetbuf_dataptr(); 

		msg->metric = metric;
		msg->seq= seq;

		unicast_send(&uc, from);
		

	}
	
}
//Calls broadcast_recv if a broadcast is recieved, executing whats in the broadcast_recv method
static const struct broadcast_callbacks broadcast_call = {broadcast_recv};

/*---------------------------------------------------------------------------*/

//Code that is executed if a unicast signal is received.
recv_uc(struct unicast_conn *c, const linkaddr_t *from)
{
	
	char *msg = (char *)packetbuf_dataptr();

	char *hello = "hello";
	char *ok = "ok";

	if(strcmp(hello, msg) == 0){

		printf("TEST FROM - %d.%d\n\n", from->u8[0], from->u8[1]);

		packetbuf_copyfrom("ok", 3);

		unicast_send(&uc, from);

	}

}
//Execute recv_uc if unicast signal is received
static const struct unicast_callbacks unicast_callbacks = {recv_uc};

/*---------------------------------------------------------------------------*/

PROCESS_THREAD(broadcast_process, ev, data)
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

	while(1) {
			
    	//Set time for etimer
			etimer_set(&etimer, 6000);

			PROCESS_WAIT_EVENT();
					
			struct beacon *msg;

			packetbuf_clear();

			msg = (struct beacon *) packetbuf_dataptr(); 

			packetbuf_set_datalen(sizeof(struct beacon));

		msg->metric = metric;
		seq = seq + 1;
		msg->seq = seq;

		//Broadcast packet to all sensors
		broadcast_send(&broadcast);
		
		//Print message that packet has been sent
		printf("broadcast message sent: metric: %d -- seq: %d\n\n", msg->metric, msg->seq);
		
		//Reste etimer
		etimer_reset(&etimer);
		
 	}

  	PROCESS_END();
}
/*---------------------------------------------------------------------------*/