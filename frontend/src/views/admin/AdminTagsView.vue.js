import { onMounted, reactive, ref } from 'vue';
import { ElMessage } from 'element-plus';
import { api } from '@/api';
const q = reactive({ page: 1, size: 20, keyword: '', tagType: '' });
const list = ref([]);
const total = ref(0);
const visible = ref(false);
const editingId = ref(null);
const form = reactive({ tagName: '', tagType: 'knowledge' });
const load = async () => {
    const data = await api.adminTags(q);
    list.value = data.list || [];
    total.value = data.total || 0;
};
const onSearch = async () => { q.page = 1; await load(); };
const onSizeChange = async () => { q.page = 1; await load(); };
const openCreate = () => {
    editingId.value = null;
    Object.assign(form, { tagName: '', tagType: 'knowledge' });
    visible.value = true;
};
const openEdit = (row) => {
    editingId.value = row.id;
    Object.assign(form, { tagName: row.tagName, tagType: row.tagType });
    visible.value = true;
};
const save = async () => {
    if (editingId.value)
        await api.adminUpdateTag(editingId.value, form);
    else
        await api.adminCreateTag(form);
    ElMessage.success('保存成功');
    visible.value = false;
    await load();
};
const remove = async (id) => {
    await api.adminDeleteTag(id, true);
    ElMessage.success('删除成功');
    await load();
};
onMounted(load);
debugger; /* PartiallyEnd: #3632/scriptSetup.vue */
const __VLS_ctx = {};
let __VLS_components;
let __VLS_directives;
const __VLS_0 = {}.ElCard;
/** @type {[typeof __VLS_components.ElCard, typeof __VLS_components.elCard, typeof __VLS_components.ElCard, typeof __VLS_components.elCard, ]} */ ;
// @ts-ignore
const __VLS_1 = __VLS_asFunctionalComponent(__VLS_0, new __VLS_0({}));
const __VLS_2 = __VLS_1({}, ...__VLS_functionalComponentArgsRest(__VLS_1));
var __VLS_4 = {};
__VLS_3.slots.default;
const __VLS_5 = {}.ElForm;
/** @type {[typeof __VLS_components.ElForm, typeof __VLS_components.elForm, typeof __VLS_components.ElForm, typeof __VLS_components.elForm, ]} */ ;
// @ts-ignore
const __VLS_6 = __VLS_asFunctionalComponent(__VLS_5, new __VLS_5({
    inline: true,
}));
const __VLS_7 = __VLS_6({
    inline: true,
}, ...__VLS_functionalComponentArgsRest(__VLS_6));
__VLS_8.slots.default;
const __VLS_9 = {}.ElFormItem;
/** @type {[typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, ]} */ ;
// @ts-ignore
const __VLS_10 = __VLS_asFunctionalComponent(__VLS_9, new __VLS_9({
    label: "关键字",
}));
const __VLS_11 = __VLS_10({
    label: "关键字",
}, ...__VLS_functionalComponentArgsRest(__VLS_10));
__VLS_12.slots.default;
const __VLS_13 = {}.ElInput;
/** @type {[typeof __VLS_components.ElInput, typeof __VLS_components.elInput, ]} */ ;
// @ts-ignore
const __VLS_14 = __VLS_asFunctionalComponent(__VLS_13, new __VLS_13({
    modelValue: (__VLS_ctx.q.keyword),
    clearable: true,
}));
const __VLS_15 = __VLS_14({
    modelValue: (__VLS_ctx.q.keyword),
    clearable: true,
}, ...__VLS_functionalComponentArgsRest(__VLS_14));
var __VLS_12;
const __VLS_17 = {}.ElFormItem;
/** @type {[typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, ]} */ ;
// @ts-ignore
const __VLS_18 = __VLS_asFunctionalComponent(__VLS_17, new __VLS_17({
    label: "类型",
}));
const __VLS_19 = __VLS_18({
    label: "类型",
}, ...__VLS_functionalComponentArgsRest(__VLS_18));
__VLS_20.slots.default;
const __VLS_21 = {}.ElInput;
/** @type {[typeof __VLS_components.ElInput, typeof __VLS_components.elInput, ]} */ ;
// @ts-ignore
const __VLS_22 = __VLS_asFunctionalComponent(__VLS_21, new __VLS_21({
    modelValue: (__VLS_ctx.q.tagType),
    placeholder: "knowledge/algorithm",
    clearable: true,
}));
const __VLS_23 = __VLS_22({
    modelValue: (__VLS_ctx.q.tagType),
    placeholder: "knowledge/algorithm",
    clearable: true,
}, ...__VLS_functionalComponentArgsRest(__VLS_22));
var __VLS_20;
const __VLS_25 = {}.ElButton;
/** @type {[typeof __VLS_components.ElButton, typeof __VLS_components.elButton, typeof __VLS_components.ElButton, typeof __VLS_components.elButton, ]} */ ;
// @ts-ignore
const __VLS_26 = __VLS_asFunctionalComponent(__VLS_25, new __VLS_25({
    ...{ 'onClick': {} },
    type: "primary",
}));
const __VLS_27 = __VLS_26({
    ...{ 'onClick': {} },
    type: "primary",
}, ...__VLS_functionalComponentArgsRest(__VLS_26));
let __VLS_29;
let __VLS_30;
let __VLS_31;
const __VLS_32 = {
    onClick: (__VLS_ctx.onSearch)
};
__VLS_28.slots.default;
var __VLS_28;
const __VLS_33 = {}.ElButton;
/** @type {[typeof __VLS_components.ElButton, typeof __VLS_components.elButton, typeof __VLS_components.ElButton, typeof __VLS_components.elButton, ]} */ ;
// @ts-ignore
const __VLS_34 = __VLS_asFunctionalComponent(__VLS_33, new __VLS_33({
    ...{ 'onClick': {} },
    type: "success",
}));
const __VLS_35 = __VLS_34({
    ...{ 'onClick': {} },
    type: "success",
}, ...__VLS_functionalComponentArgsRest(__VLS_34));
let __VLS_37;
let __VLS_38;
let __VLS_39;
const __VLS_40 = {
    onClick: (__VLS_ctx.openCreate)
};
__VLS_36.slots.default;
var __VLS_36;
var __VLS_8;
const __VLS_41 = {}.ElTable;
/** @type {[typeof __VLS_components.ElTable, typeof __VLS_components.elTable, typeof __VLS_components.ElTable, typeof __VLS_components.elTable, ]} */ ;
// @ts-ignore
const __VLS_42 = __VLS_asFunctionalComponent(__VLS_41, new __VLS_41({
    data: (__VLS_ctx.list),
}));
const __VLS_43 = __VLS_42({
    data: (__VLS_ctx.list),
}, ...__VLS_functionalComponentArgsRest(__VLS_42));
__VLS_44.slots.default;
const __VLS_45 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_46 = __VLS_asFunctionalComponent(__VLS_45, new __VLS_45({
    prop: "id",
    label: "ID",
    width: "90",
}));
const __VLS_47 = __VLS_46({
    prop: "id",
    label: "ID",
    width: "90",
}, ...__VLS_functionalComponentArgsRest(__VLS_46));
const __VLS_49 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_50 = __VLS_asFunctionalComponent(__VLS_49, new __VLS_49({
    prop: "tagName",
    label: "标签名",
}));
const __VLS_51 = __VLS_50({
    prop: "tagName",
    label: "标签名",
}, ...__VLS_functionalComponentArgsRest(__VLS_50));
const __VLS_53 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_54 = __VLS_asFunctionalComponent(__VLS_53, new __VLS_53({
    prop: "tagType",
    label: "类型",
    width: "180",
}));
const __VLS_55 = __VLS_54({
    prop: "tagType",
    label: "类型",
    width: "180",
}, ...__VLS_functionalComponentArgsRest(__VLS_54));
const __VLS_57 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_58 = __VLS_asFunctionalComponent(__VLS_57, new __VLS_57({
    label: "操作",
    width: "180",
}));
const __VLS_59 = __VLS_58({
    label: "操作",
    width: "180",
}, ...__VLS_functionalComponentArgsRest(__VLS_58));
__VLS_60.slots.default;
{
    const { default: __VLS_thisSlot } = __VLS_60.slots;
    const [scope] = __VLS_getSlotParams(__VLS_thisSlot);
    const __VLS_61 = {}.ElButton;
    /** @type {[typeof __VLS_components.ElButton, typeof __VLS_components.elButton, typeof __VLS_components.ElButton, typeof __VLS_components.elButton, ]} */ ;
    // @ts-ignore
    const __VLS_62 = __VLS_asFunctionalComponent(__VLS_61, new __VLS_61({
        ...{ 'onClick': {} },
        link: true,
        type: "primary",
    }));
    const __VLS_63 = __VLS_62({
        ...{ 'onClick': {} },
        link: true,
        type: "primary",
    }, ...__VLS_functionalComponentArgsRest(__VLS_62));
    let __VLS_65;
    let __VLS_66;
    let __VLS_67;
    const __VLS_68 = {
        onClick: (...[$event]) => {
            __VLS_ctx.openEdit(scope.row);
        }
    };
    __VLS_64.slots.default;
    var __VLS_64;
    const __VLS_69 = {}.ElButton;
    /** @type {[typeof __VLS_components.ElButton, typeof __VLS_components.elButton, typeof __VLS_components.ElButton, typeof __VLS_components.elButton, ]} */ ;
    // @ts-ignore
    const __VLS_70 = __VLS_asFunctionalComponent(__VLS_69, new __VLS_69({
        ...{ 'onClick': {} },
        link: true,
        type: "danger",
    }));
    const __VLS_71 = __VLS_70({
        ...{ 'onClick': {} },
        link: true,
        type: "danger",
    }, ...__VLS_functionalComponentArgsRest(__VLS_70));
    let __VLS_73;
    let __VLS_74;
    let __VLS_75;
    const __VLS_76 = {
        onClick: (...[$event]) => {
            __VLS_ctx.remove(scope.row.id);
        }
    };
    __VLS_72.slots.default;
    var __VLS_72;
}
var __VLS_60;
var __VLS_44;
__VLS_asFunctionalElement(__VLS_intrinsicElements.div, __VLS_intrinsicElements.div)({
    ...{ style: {} },
});
const __VLS_77 = {}.ElPagination;
/** @type {[typeof __VLS_components.ElPagination, typeof __VLS_components.elPagination, ]} */ ;
// @ts-ignore
const __VLS_78 = __VLS_asFunctionalComponent(__VLS_77, new __VLS_77({
    ...{ 'onCurrentChange': {} },
    ...{ 'onSizeChange': {} },
    currentPage: (__VLS_ctx.q.page),
    pageSize: (__VLS_ctx.q.size),
    layout: "total, sizes, prev, pager, next, jumper",
    pageSizes: ([10, 20, 50]),
    total: (__VLS_ctx.total),
}));
const __VLS_79 = __VLS_78({
    ...{ 'onCurrentChange': {} },
    ...{ 'onSizeChange': {} },
    currentPage: (__VLS_ctx.q.page),
    pageSize: (__VLS_ctx.q.size),
    layout: "total, sizes, prev, pager, next, jumper",
    pageSizes: ([10, 20, 50]),
    total: (__VLS_ctx.total),
}, ...__VLS_functionalComponentArgsRest(__VLS_78));
let __VLS_81;
let __VLS_82;
let __VLS_83;
const __VLS_84 = {
    onCurrentChange: (__VLS_ctx.load)
};
const __VLS_85 = {
    onSizeChange: (__VLS_ctx.onSizeChange)
};
var __VLS_80;
const __VLS_86 = {}.ElDialog;
/** @type {[typeof __VLS_components.ElDialog, typeof __VLS_components.elDialog, typeof __VLS_components.ElDialog, typeof __VLS_components.elDialog, ]} */ ;
// @ts-ignore
const __VLS_87 = __VLS_asFunctionalComponent(__VLS_86, new __VLS_86({
    modelValue: (__VLS_ctx.visible),
    title: (__VLS_ctx.editingId ? '编辑标签' : '新增标签'),
}));
const __VLS_88 = __VLS_87({
    modelValue: (__VLS_ctx.visible),
    title: (__VLS_ctx.editingId ? '编辑标签' : '新增标签'),
}, ...__VLS_functionalComponentArgsRest(__VLS_87));
__VLS_89.slots.default;
const __VLS_90 = {}.ElForm;
/** @type {[typeof __VLS_components.ElForm, typeof __VLS_components.elForm, typeof __VLS_components.ElForm, typeof __VLS_components.elForm, ]} */ ;
// @ts-ignore
const __VLS_91 = __VLS_asFunctionalComponent(__VLS_90, new __VLS_90({
    labelWidth: "90px",
}));
const __VLS_92 = __VLS_91({
    labelWidth: "90px",
}, ...__VLS_functionalComponentArgsRest(__VLS_91));
__VLS_93.slots.default;
const __VLS_94 = {}.ElFormItem;
/** @type {[typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, ]} */ ;
// @ts-ignore
const __VLS_95 = __VLS_asFunctionalComponent(__VLS_94, new __VLS_94({
    label: "标签名",
}));
const __VLS_96 = __VLS_95({
    label: "标签名",
}, ...__VLS_functionalComponentArgsRest(__VLS_95));
__VLS_97.slots.default;
const __VLS_98 = {}.ElInput;
/** @type {[typeof __VLS_components.ElInput, typeof __VLS_components.elInput, ]} */ ;
// @ts-ignore
const __VLS_99 = __VLS_asFunctionalComponent(__VLS_98, new __VLS_98({
    modelValue: (__VLS_ctx.form.tagName),
}));
const __VLS_100 = __VLS_99({
    modelValue: (__VLS_ctx.form.tagName),
}, ...__VLS_functionalComponentArgsRest(__VLS_99));
var __VLS_97;
const __VLS_102 = {}.ElFormItem;
/** @type {[typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, ]} */ ;
// @ts-ignore
const __VLS_103 = __VLS_asFunctionalComponent(__VLS_102, new __VLS_102({
    label: "类型",
}));
const __VLS_104 = __VLS_103({
    label: "类型",
}, ...__VLS_functionalComponentArgsRest(__VLS_103));
__VLS_105.slots.default;
const __VLS_106 = {}.ElInput;
/** @type {[typeof __VLS_components.ElInput, typeof __VLS_components.elInput, ]} */ ;
// @ts-ignore
const __VLS_107 = __VLS_asFunctionalComponent(__VLS_106, new __VLS_106({
    modelValue: (__VLS_ctx.form.tagType),
}));
const __VLS_108 = __VLS_107({
    modelValue: (__VLS_ctx.form.tagType),
}, ...__VLS_functionalComponentArgsRest(__VLS_107));
var __VLS_105;
var __VLS_93;
{
    const { footer: __VLS_thisSlot } = __VLS_89.slots;
    const __VLS_110 = {}.ElButton;
    /** @type {[typeof __VLS_components.ElButton, typeof __VLS_components.elButton, typeof __VLS_components.ElButton, typeof __VLS_components.elButton, ]} */ ;
    // @ts-ignore
    const __VLS_111 = __VLS_asFunctionalComponent(__VLS_110, new __VLS_110({
        ...{ 'onClick': {} },
    }));
    const __VLS_112 = __VLS_111({
        ...{ 'onClick': {} },
    }, ...__VLS_functionalComponentArgsRest(__VLS_111));
    let __VLS_114;
    let __VLS_115;
    let __VLS_116;
    const __VLS_117 = {
        onClick: (...[$event]) => {
            __VLS_ctx.visible = false;
        }
    };
    __VLS_113.slots.default;
    var __VLS_113;
    const __VLS_118 = {}.ElButton;
    /** @type {[typeof __VLS_components.ElButton, typeof __VLS_components.elButton, typeof __VLS_components.ElButton, typeof __VLS_components.elButton, ]} */ ;
    // @ts-ignore
    const __VLS_119 = __VLS_asFunctionalComponent(__VLS_118, new __VLS_118({
        ...{ 'onClick': {} },
        type: "primary",
    }));
    const __VLS_120 = __VLS_119({
        ...{ 'onClick': {} },
        type: "primary",
    }, ...__VLS_functionalComponentArgsRest(__VLS_119));
    let __VLS_122;
    let __VLS_123;
    let __VLS_124;
    const __VLS_125 = {
        onClick: (__VLS_ctx.save)
    };
    __VLS_121.slots.default;
    var __VLS_121;
}
var __VLS_89;
var __VLS_3;
var __VLS_dollars;
const __VLS_self = (await import('vue')).defineComponent({
    setup() {
        return {
            q: q,
            list: list,
            total: total,
            visible: visible,
            editingId: editingId,
            form: form,
            load: load,
            onSearch: onSearch,
            onSizeChange: onSizeChange,
            openCreate: openCreate,
            openEdit: openEdit,
            save: save,
            remove: remove,
        };
    },
});
export default (await import('vue')).defineComponent({
    setup() {
        return {};
    },
});
; /* PartiallyEnd: #4569/main.vue */
