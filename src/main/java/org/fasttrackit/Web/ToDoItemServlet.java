package org.fasttrackit.Web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fasttrackit.domain.ToDoItem;
import org.fasttrackit.service.ToDoItemService;
import org.fasttrackit.transfer.SaveToDoItemRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = "/to-do-items")
public class ToDoItemServlet extends HttpServlet {

    private ToDoItemService toDoItemService=new ToDoItemService();

    //post=creare
    //put=update
    //get=read
    //delete=delete

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //object mapper-----------read deserializeaza
        //             -----------write serializeaza

        ObjectMapper objectMapper=new ObjectMapper();
        SaveToDoItemRequest saveToDoItemRequest = objectMapper.readValue(req.getReader(), SaveToDoItemRequest.class);

        //in semnatura metodei nu mai pot adauga exceptii deoarece

        try {
            toDoItemService.createToDoItem(saveToDoItemRequest);
        } catch (Exception e) {
            resp.sendError(500,"Internal error: "+e.getMessage());
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<ToDoItem> toDoItems = toDoItemService.getToDoItems();
            //endpoint=expunem aplicatia
            //transformam obiectul nostru intr-o reprezentare JSON
            //serializing or marshalling
            ObjectMapper objectMapper=new ObjectMapper();
            String responseJSON = objectMapper.writeValueAsString (toDoItems);

            //mime type (content type)
            resp.setContentType ("application/json");
            resp.getWriter ().print (responseJSON);
            resp.getWriter ().flush ();

        } catch (Exception e) {
            resp.sendError (500, "There was a error processing your request. " + e.getMessage ());
        }
    }
}

