$(document).ready(function () {
    let dataTable;
    let isEditing = false;
    let inventarioModal;
    const formId = '#formInventario';

    const API_BASE = '/inventarios/api';
    const ENDPOINTS = {
        list: `${API_BASE}/listar`,
        save: `${API_BASE}/guardar`,
        get: (id) => `${API_BASE}/${id}`,
        products: `${API_BASE}/productos`,
    };

    initializeDataTable();
    inventarioModal = new bootstrap.Modal(document.getElementById('inventarioModal'));
    loadSales();
    setupEventListeners();

    function initializeDataTable() {
        dataTable = $('#tablaInventarios').DataTable({
            responsive: true,
            processing: true,
            ajax: { url: ENDPOINTS.list, dataSrc: 'data' },
            columns: [
                { data: 'id' },
                { data: 'producto.nombre' },
                { data: 'stock' },
                { data: 'stockMinimo' },
                {
                    data: null, orderable: false, searchable: false,
                    render: (data, type, row) => createActionButton(row)
                }
            ],
            language: { url: "//cdn.datatables.net/plug-ins/1.13.6/i18n/es-ES.json" }
        });
    }

    function createActionButton(row) {
        return `
            <div class="d-flex gap-1">
                <button data-id="${row.id}" class="action-btn action-btn-edit action-more" title="Más acciones"><svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-pencil-square" viewBox="0 0 16 16"><path d="M15.502 1.94a.5.5 0 0 1 0 .706L14.459 3.69l-2-2L13.502.646a.5.5 0 0 1 .707 0l1.293 1.293zm-1.75 2.456-2-2L4.939 9.21a.5.5 0 0 0-.121.196l-.805 2.414a.25.25 0 0 0 .316.316l2.414-.805a.5.5 0 0 0 .196-.12l6.813-6.814z"/><path fill-rule="evenodd" d="M1 13.5A1.5 1.5 0 0 0 2.5 15h11a1.5 1.5 0 0 0 1.5-1.5v-6a.5.5 0 0 0-1 0v6a.5.5 0 0 1-.5.5h-11a.5.5 0 0 1-.5-.5v-11a.5.5 0 0 1 .5-.5H9a.5.5 0 0 0 0-1H2.5A1.5 1.5 0 0 0 1 2.5z"/></svg></button>
            </div>
        `;
    }

    function setupEventListeners() {
        // $('#btnNuevoRegistro').on('click', openModalForNew);
        $(formId).on('submit', (e) => { e.preventDefault(); saveInventario(); });
        $('#tablaInventarios tbody').on('click', '.action-more', handleMore);
    }

    function reloadTable() { dataTable.ajax.reload(); }

    function handleMore() {
        AppUtils.showNotification("Hola");
    }

    function loadSales() {}

    function saveInventario() {
        const formData = {
            id: $('#id').val() || null, nombre: $('#nombre').val().trim(),
            usuario: $('#usuario').val().trim(), apellidoPaterno: $('#apellidoPaterno').val().trim(),
            apellidoMaterno: $('#apellidoMaterno').val().trim(), correo: $('#correo').val().trim(),
            telefono: $('#telefono').val().trim(), direccion: $('#direccion').val().trim(),
            ndocumento: $('#ndocumento').val().trim(), clave: $('#clave').val(),
            tipodocumento: { id: $('#id_tipodocumento').val() },
            perfil: { id: $('#id_perfil').val() }
        };

        if (isEditing && !formData.clave) { delete formData.clave; }

        AppUtils.showLoading(true);
        fetch(ENDPOINTS.save, {
            method: 'POST', headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(formData)
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    usuarioModal.hide();
                    AppUtils.showNotification(data.message, 'success');
                    reloadTable();
                } else {
                    if (data.errors) {
                        Object.keys(data.errors).forEach(field => $(`#${field}-error`).text(data.errors[field]));
                    } else { AppUtils.showNotification(data.message, 'error'); }
                }
            })
            .catch(error => AppUtils.showNotification('Error de conexión', 'error'))
            .finally(() => AppUtils.showLoading(false));
    }


    // function openModalForNew() {
    //     isEditing = false;
    //     AppUtils.clearForm(formId);
    //     const ndocumentoInput = $('#ndocumento');
    //     ndocumentoInput.removeAttr('maxlength');
    //     ndocumentoInput.attr('placeholder', 'N° de Documento');
    //     ndocumentoInput.removeData('expected-length');
    //     ndocumentoInput.removeData('validation-message');
    //
    //     $('#modalTitle').text('Agregar Usuario');
    //     usuarioModal.show();
    // }

});