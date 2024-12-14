package edu.java.bot.command;

import edu.java.bot.clients.scrapper.PollPreviewResponse;
import edu.java.bot.model.PollDto;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class CommandUtils {
    private CommandUtils() {
    }

    public static String createHelpMessage(List<? extends AbstractCommand> commands) {
        StringBuilder message = new StringBuilder();
        message.append("Доступные команды\n");
        message.append(
            commands.stream()
                .map(command -> command.getCommand() + " " + command.getDescription())
                .collect(Collectors.joining("\n"))
        );
        return message.toString();
    }

    public static String createPollsPreviewMessage(List<PollPreviewResponse> polls) {
        StringBuilder message = new StringBuilder();
        message.append("Доступные опросы\n");
        message.append("(опросы, созданные вами отмечены *)\n\n");
        message.append(
            IntStream.range(0, polls.size())
                .mapToObj(i -> i + 1 + (polls.get(i).getIsOwner() ? "*) " : ") ") + polls.get(i).getTitle())
                .collect(Collectors.joining("\n"))
        );
        return message.toString();
    }

    public static String createPollsInfoMessage(PollDto poll) {
        StringBuilder message = new StringBuilder();
        message.append(poll.getTitle() + "\n");

        for (int i = 0; i < poll.getOptions().size(); i++) {
            message.append(i + 1 + ") " + poll.getOptions().get(i).getOption() + " - ");
            if (poll.getOptions().get(i).getIsTaken()) {
                message.append(poll.getOptions().get(i).getFirstname() + " " + poll.getOptions().get(i).getLastname());
            } else {
                message.append("свободно");
            }

            message.append("\n");
        }
        return message.toString();
    }

//    public static String createListMessage(List<Link> links) {
//        if (links == null || links.isEmpty()) {
//            return "You haven't added any links yet";
//        }
//
//        StringBuilder message = new StringBuilder();
//        message.append("Tracking links\n");
//        message.append(
//            IntStream.range(0, links.size())
//                .mapToObj(i -> i + 1 + ") " + links.get(i))
//                .collect(Collectors.joining("\n"))
//        );
//        return message.toString();
//    }
}
