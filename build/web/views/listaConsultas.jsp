<%@page import="models.Medico"%>
<%@page import="java.util.ArrayList"%>
<%@page import="models.Consulta"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%
    String tipoConta = (String) session.getAttribute("tipoUsuario");
%>
<head>
    <link href="bootstrap/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="bootstrap/site.css" rel="stylesheet" type="text/css"/>
    <link href="bootstrap/ionic.bootstrap.css" rel="stylesheet" type="text/css"/>
    <script src="https://code.jquery.com/jquery-3.6.2.min.js"></script>

</head>
<div class="page">
    <jsp:include page="menu.jsp" />
    <main>

        <jsp:include page="header.jsp" />

        <article class="content px-4">
            <div class="mt-5">

                <h1>Área Restrita</h1>
                <br>
                <h2>Lista de Consultas</h2>
                <br>

                <div class="mb-3" style="max-width:300px">
                    <h3 for="data" class="form-label">FILTRAR POR MÉDICO</h1>
                        <select class="form-select d-block form-control" id="idmedico" name="idmedico" required>
                            <option value="0">FILTRAR POR MÉDICO</option>
                            <%
                                ArrayList<Medico> listaDeMedico = (ArrayList<Medico>) request.getAttribute("listaDeMedicos");
                                String selectedMedico = request.getAttribute("selectedMedico").toString();
                                for (Medico medico : listaDeMedico) {
                                    String str = "";
                                    if (selectedMedico != null && medico.getId() == Integer.parseInt(selectedMedico)) {
                                        str = "selected";
                                    }
                                    out.println("<option " + str + " value= '" + medico.getId() + "'>");
                                    out.println(medico.getNome());
                                    out.println("</option>");
                                }
                            %>
                        </select>
                </div>
                <br>

                <%  String msgOperacaoRealizada = (String) request.getAttribute("msgOperacaoRealizada");
                    if ((msgOperacaoRealizada != null) && (!msgOperacaoRealizada.isEmpty())) {%>
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <strong><%= msgOperacaoRealizada%></strong>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <% } %>
                <%if (tipoConta != Enums.TipoConta.Adm) {%>
                <a href="consulta?acao=Incluir" class="mb-2 btn btn-primary">Incluir</a>
                <% } %>
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th scope="col">Id</th>
                                <th scope="col">Descrição</th>
                                <th scope="col">Data</th>
                                <th scope="col">Médico</th>
                                <th scope="col">Paciente</th>
                                <th scope="col">Realizada?</th>
                                <th scope="col">Açoes</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                ArrayList<Consulta> listaConsultas = (ArrayList<Consulta>) request.getAttribute("listaConsultas");

                                for (Consulta consulta : listaConsultas) {
                                    out.println("<tr>");
                                    out.println("<th>" + consulta.getId() + "</th>");
                                    out.println("<td>" + consulta.getDescricao() + "</td>");
                                    out.println("<td>" + consulta.getData() + "</td>");
                                    out.println("<td>" + consulta.getMedico() + "</td>");
                                    out.println("<td>" + consulta.getPaciente() + "</td>");
                                    out.println("<td>" + consulta.getRealizada() + "</td>");%>
                        <td>
                            <%if (tipoConta != Enums.TipoConta.Adm) {%>
                            <a href="consulta?acao=Alterar&id=<%=consulta.getId()%>" class="btn btn-warning">Alterar</a>
                            <a href="consulta?acao=Excluir&id=<%=consulta.getId()%>" class="btn btn-danger">Excluir</a></td>
                            <%   out.println("</tr>");
                                }
                            %>
                            <% }%>
                        </tbody>
                    </table>
                </div>
            </div>
        </article>
    </main>
</div>

<script>
    $("#idmedico").change(function () {
        var val = $(this).val();
        window.location.href = "/consulta?idMedico=" + val;
    });
</script>