package org.demo;

import org.axonframework.commandhandling.AggregateAnnotationCommandHandler;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.distributed.AnnotationRoutingStrategy;
import org.axonframework.commandhandling.distributed.DistributedCommandBus;
import org.axonframework.commandhandling.distributed.commandfilter.AcceptAll;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.eventhandling.EventListener;
import org.axonframework.eventhandling.SimpleEventHandlerInvoker;
import org.axonframework.eventhandling.SubscribingEventProcessor;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.axonframework.jgroups.commandhandling.JGroupsConnector;
import org.axonframework.serialization.xml.XStreamSerializer;
import org.jgroups.JChannel;

import java.util.Random;

public class DemoApplication {
    private JGroupsConnector connector;

    public static void main(String[] args) throws Exception {
        DemoApplication demoApplication = new DemoApplication();
        demoApplication.run();
        System.exit(0);
    }

    private void run() throws Exception {
        EventStore eventStore = new EmbeddedEventStore(new InMemoryEventStorageEngine());
        CommandBus commandBus = configureDistributedCommandBus();

        Repository<Item> repository = new EventSourcingRepository<>(Item.class, eventStore);

        new AggregateAnnotationCommandHandler<>(Item.class, repository).subscribe(commandBus);

        CommandGateway commandGateway = new DefaultCommandGateway(commandBus);

        new SubscribingEventProcessor("processor", new SimpleEventHandlerInvoker((EventListener) event -> {
            System.out.println(event.getPayload());
        }), eventStore).start();

        Thread.sleep(5000);

        if (System.getenv("PRIMARY").equals(("yes"))) {
            for (int a = 0; a < 100; a++) {
                int itemId = new Random().nextInt(50000);
                System.out.println("Created item " + a + " id: " + itemId);
                commandGateway.sendAndWait(new CreateItem(Integer.toString(itemId), Integer.toString(a)));
            }
        }

        connector.disconnect();

        Thread.sleep(5000);
    }

    private CommandBus configureDistributedCommandBus() throws Exception {
        CommandBus commandBus = new SimpleCommandBus();

        JChannel channel = new JChannel(getClass().getClassLoader().getResourceAsStream("channel.xml"));

        connector = new JGroupsConnector(commandBus, channel, "axon-jgroups-demo", new XStreamSerializer(), new AnnotationRoutingStrategy());
        connector.updateMembership(100, AcceptAll.INSTANCE);

        connector.connect();
        connector.awaitJoined();

        return new DistributedCommandBus(connector, connector);
    }
}
