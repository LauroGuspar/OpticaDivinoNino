$(document).ready(function () {
    let isEditing = false;
    let dataTable;
    let opcionModal;
    const formid = '#formOpcion';

    const API_BASE = '/opciones/api';
    const ENDPOINTS = {
        list: `${API_BASE}/listar`,
        save: `${API_BASE}/guardar`,
        get: (id) => `${API_BASE}/${id}`,
    };

    initializeDataTable();
    opcionModal = new bootstrap.Modal(document.getElementById('opcionModal'));
    setupEventListeners();

    function initializeDataTable() {
        dataTable = $('#tablaOpciones').DataTable({
            responsive: true,
            processing: true,
            ajax: { url: ENDPOINTS.list, dataSrc: 'data' },
            columns: [
                { data: 'id' },
                { data: 'nombre' },
                { data: 'ruta' },
            ],
            language: { url: "//cdn.datatables.net/plug-ins/1.13.6/i18n/es-ES.json" }
        });
    }

    function setupEventListeners() {
        $('#btnNuevoRegistro').on('click', openModalForNew);
        $(formid).on('submit', (e) => { e.preventDefault(); saveOpcion(); });
    }

    function reloadTable() { dataTable.ajax.reload(); }

    function saveOpcion() {
        const opcionData = {
            id: $('#id').val() || null,
            nombre: $('#nombre').val().trim(),
            ruta: $('#ruta').val().trim(),
        };

        if (!opcionData.nombre) {
            showFieldError('nombre', 'El nombre es obligatorio');
            return;
        }

        AppUtils.showLoading(true);
        fetch(ENDPOINTS.save, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(opcionData)
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    opcionModal.hide();
                    AppUtils.showNotification(data.message, 'success');
                    reloadTable();
                } else {
                    AppUtils.showNotification(data.message, 'error');
                }
            })
            .catch(error => AppUtils.showNotification('Error de conexión', 'error'))
            .finally(() => AppUtils.showLoading(false));
    }

    function openModalForNew() {
        isEditing = false;
        AppUtils.clearForm(formid);
        $('#modalTitle').text('Agregar Opcion');
        opcionModal.show();
    }
});