$(document).ready(function () {
    $('#tbCategorias').DataTable({
        "language": {
            "search": "Buscar:",
            "lengthMenu": "Exibir _MENU_ registros por página",
            "zeroRecords": "Nenhuma registro encontrado",
            "info": "Mostrando página _PAGE_ de _PAGES_",
            "infoEmpty": "Nenhuma registro disponível",
            "infoFiltered": "(filtrado de _MAX_ registros totais)",
            "paginate": {
                "first": "Primeiro",
                "last": "Último",
                "next": "Próximo",
                "previous": "Anterior"
            },
        },
        'paging': true,
        'lengthChange': true,
        'searching': true,
        'ordering': true,
        'info': true,
        'autoWidth': false,
        "columns": [
            null,
            {"orderable": false, className: "text-center"}
        ]
    });
    $('#tbUsuarios, #tbAtendentes, #tbAtendimentos').DataTable({
        "language": {
            "search": "Buscar:",
            "lengthMenu": "Exibir _MENU_ registros por página",
            "zeroRecords": "Nenhuma registro encontrado",
            "info": "Mostrando página _PAGE_ de _PAGES_",
            "infoEmpty": "Nenhuma registro disponível",
            "infoFiltered": "(filtrado de _MAX_ registros totais)",
            "paginate": {
                "first": "Primeiro",
                "last": "Último",
                "next": "Próximo",
                "previous": "Anterior"
            },
        },
        'paging': true,
        'lengthChange': true,
        'searching': true,
        'ordering': true,
        'info': true,
        'autoWidth': false,
        "columns": [
            null,null,null,
            {"orderable": false, className: "text-center"}
        ]
    });
});

