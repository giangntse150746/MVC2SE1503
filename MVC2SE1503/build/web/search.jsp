<%-- 
    Document   : search
    Created on : Feb 25, 2021, 9:06:53 AM
    Author     : Admin
--%>

<%@page import="tblDemo.TblDemoDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search</title>
    </head>
    <body>
        <h2>Search Page</h2>
        <h1>ACCESS SUCCESSFUL!!!</h1>
        <div id="main-area">
            <form action="DispatchServlet">
                Search Value <input type="text" name="txtSearchValue"
                                value="<%= request.getParameter("txtSearchValue") %>"/>
                <br/>
                <input type="submit" value="Search" name="btnAction" />
            </form>
        </div>
        <!--make a scripting element area-->
        <%
            String searchValue = request.getParameter("txtSearchValue");
            if (searchValue != null) {
                List<TblDemoDTO> result = 
                            (List<TblDemoDTO>)request.getAttribute("SEARCH_RESULT");
                if (result != null) {
                    %>
                    <table border="1">
                        <thead>
                            <tr>
                                <th>No.</th>
                                <th>Username</th>
                                <th>Password</th>
                                <th>Full Name</th>
                                <th>Role</th>
                                <th>Delete</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                            int count = 0;
                            for (TblDemoDTO dto : result) {
                                String urlRewriting = "/DispatchServlet"
                                            + "?btnAction=sa"
                                            + "&pk=" + dto.getUsername()
                                            + "&lastSearchValue="
                                            + searchValue;
                                %>
                                <tr>
                                    <td>
                                        <%= ++count %>
                                    </td>
                                    <td>
                                        <%= dto.getUsername() %>
                                    </td>
                                    <td>
                                        <%= dto.getPassword() %>
                                    </td>
                                    <td>
                                        <%= dto.getFullname() %>
                                    </td>
                                    <td>
                                        <%= dto.isRole() == true ? "is Admin" : "is not Admin" %>
                                    </td>
                                    <td>
                                        <a href="<%= urlRewriting %>">Delete</a>
                                    </td>
                                </tr>
                                <%
                            }
                            %>
                        </tbody>
                    </table>
                <%
                } else {
                    %>
                    <h1>No Record Found!!!</h1>
                    <%
                }//end if has no record
            }//end if searchValue has a value
        %>
    </body>
</html>
