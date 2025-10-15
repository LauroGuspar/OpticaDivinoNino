$(document).ready(function () {
    let dataTable;
    let isEditing = false;
    let ventaModal;
    const formId = '#formUsuario';

    const API_BASE = '/ventas/api';
    const ENDPOINTS = {
        list: `${API_BASE}/listar`,
        save: `${API_BASE}/guardar`,
        get: (id) => `${API_BASE}/${id}`,
        delete: (id) => `${API_BASE}/eliminar/${id}`,
        products: `${API_BASE}/productos`,
        payMethods: `${API_BASE}/formapagos`,
        sellType: `${API_BASE}/tipoventas`,
        users: `${API_BASE}/usuarios`,
        toggleStatus: (id) => `${API_BASE}/cambiar-estado/${id}`,
    };

    initializeDataTable();
    ventaModal = new bootstrap.Modal(document.getElementById('ventaModal'));
    loadProductos();
    loadTipoVentas();
    loadFormaPagos();
    setupEventListeners();

    function initializeDataTable() {
        dataTable = $('#tablaVentas').DataTable({
            responsive: true,
            processing: true,
            ajax: { url: ENDPOINTS.list, dataSrc: 'data' },
            columns: [
                { data: 'id', title: 'ID' },
                { data: 'cliente.nombre', title: 'Cliente' },
                { data: 'fecha', title: 'Fecha' },
                { data: 'usuario.nombre', title: 'Empleado' },
                { data: 'total', title: 'Total' },
                { data: 'formaPago.nombre', title: 'Forma de pago' },
                { data: 'deuda', title: 'Deuda' },
                { data: 'situacion', title: 'Situación'},
                {
                    data: 'estado', title: 'Estado',
                    render: (data) => data === 1 ? '<span class="badge text-bg-success">Activo</span>' : '<span class="badge text-bg-danger">Inactivo</span>'
                },
                {
                    data: null, orderable: false, searchable: false, title: 'Acciones',
                    render: (data, type, row) => AppUtils.createActionButtons(row)
                }
            ],
            language: { url: "//cdn.datatables.net/plug-ins/1.13.6/i18n/es-ES.json" }
        });
    }

    function setupEventListeners() {
        $('#btnNuevoRegistro').on('click', openModalForNew);
        $(formId).on('submit', (e) => { e.preventDefault(); saveUsuario(); });
        $('#tablaVentas tbody').on('click', '.action-edit', handleEdit);
        $('#tablaVentas tbody').on('click', '.action-status', handleToggleStatus);
        $('#tablaVentas tbody').on('click', '.action-delete', handleDelete);

    }

    function reloadTable() { dataTable.ajax.reload(); }

    // function loadProductos() {
    //     fetch(ENDPOINTS.products)
    //         .then(response => response.json())
    //         .then(data => {
    //             if (data.success) {
    //                 const select = $('#id_producto');
    //                 select.empty().append('<option value="" disabled selected>Selecciona un Producto</option>');
    //                 data.data.forEach(profile => select.append(`<option value="${profile.id}">${profile.nombre}</option>`));
    //             } else { AppUtils.showNotification('Error al cargar productos', 'error'); }
    //         }).catch(error => console.error('Error cargando productos:', error));
    // }

    function loadTiposDocumento() {
        fetch(ENDPOINTS.tiposDocumento)
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    const select = $('#id_tipodocumento');
                    select.empty().append('<option value="" disabled selected>Selecciona un Tipo</option>');
                    data.data.forEach(doc => select.append(`<option value="${doc.id}">${doc.nombre}</option>`));
                } else { AppUtils.showNotification('Error al cargar tipos de documento', 'error'); }
            }).catch(error => console.error('Error cargando tipos de documento:', error));
    }

    function saveVenta() {

        const formData = {
            id: $('#id').val() || null,
            nombre: $('#nombre').val().trim(),
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

    function handleEdit() {
        const id = $(this).data('id');
        AppUtils.showLoading(true);
        fetch(ENDPOINTS.get(id))
            .then(response => response.json())
            .then(data => {
                if (data.success) { openModalForEdit(data.data); }
                else { AppUtils.showNotification('Error al cargar venta', 'error'); }
            })
            .catch(error => AppUtils.showNotification('Error de conexión', 'error'))
            .finally(() => AppUtils.showLoading(false));
    }

    function handleToggleStatus() {
        const id = $(this).data('id');
        AppUtils.showLoading(true);
        fetch(ENDPOINTS.toggleStatus(id), { method: 'POST' })
            .then(response => response.json())
            .then(data => {
                if (data.success) { AppUtils.showNotification(data.message, 'success'); reloadTable(); }
                else { AppUtils.showNotification(data.message, 'error'); }
            })
            .catch(error => AppUtils.showNotification('Error de conexión', 'error'))
            .finally(() => AppUtils.showLoading(false));
    }

    function handleDelete() {
        const id = $(this).data('id');
        Swal.fire({
            title: '¿Estás seguro?', text: "¡El venta será marcado como eliminado!",
            icon: 'warning', showCancelButton: true, confirmButtonColor: '#dc3545',
            cancelButtonColor: '#6c757d', confirmButtonText: 'Sí, ¡eliminar!', cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                AppUtils.showLoading(true);
                fetch(ENDPOINTS.delete(id), { method: 'DELETE' })
                    .then(response => response.json())
                    .then(data => {
                        if (data.success) { AppUtils.showNotification(data.message, 'success'); reloadTable(); }
                        else { AppUtils.showNotification(data.message, 'error'); }
                    })
                    .catch(error => AppUtils.showNotification('Error de conexión', 'error'))
                    .finally(() => AppUtils.showLoading(false));
            }
        });
    }

    function openModalForNew() {
        isEditing = false;
        AppUtils.clearForm(formId);
        const ndocumentoInput = $('#ndocumento');
        ndocumentoInput.removeAttr('maxlength');
        ndocumentoInput.attr('placeholder', 'N° de Documento');
        ndocumentoInput.removeData('expected-length');
        ndocumentoInput.removeData('validation-message');

        $('#modalTitle').text('Agregar Venta');
        ventaModal.show();
    }

    function openModalForEdit(venta) {
        isEditing = true;
        AppUtils.clearForm(formId);
        $('#modalTitle').text('Editar Venta');

        $('#id').val(usuario.id);
        $('#nombre').val(usuario.nombre);
        $('#usuario').val(usuario.usuario);
        $('#apellidoPaterno').val(usuario.apellidoPaterno);
        $('#apellidoMaterno').val(usuario.apellidoMaterno);
        $('#correo').val(usuario.correo);
        $('#telefono').val(usuario.telefono);
        $('#direccion').val(usuario.direccion);
        $('#ndocumento').val(usuario.ndocumento);
        $('#id_perfil').val(usuario.perfil ? usuario.perfil.id : '');
        $('#clave').attr('placeholder', 'Dejar en blanco para no cambiar');

        if (usuario.tipodocumento) {
            $('#id_tipodocumento').val(usuario.tipodocumento.id).trigger('change');
            $('#ndocumento').val(usuario.ndocumento);
        }
        ventaModal.show();
    }
});