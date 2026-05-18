import { onMounted, reactive, ref } from 'vue';
import { ElMessage } from 'element-plus';
import { api } from '@/api';
const q = reactive({ page: 1, size: 20, keyword: '', resourceType: '', status: undefined });
const list = ref([]);
const total = ref(0);
const visible = ref(false);
const editingId = ref(null);
const form = reactive({ resourceType: 'article', title: '', url: '', status: 1 });
const knowledgeText = ref('');
const detailVisible = ref(false);
const detail = ref({});
const load = async () => {
    const data = await api.adminResources(q);
    list.value = data.list || [];
    total.value = data.total || 0;
};
const onSearch = async () => { q.page = 1; await load(); };
const onSizeChange = async () => { q.page = 1; await load(); };
const openCreate = () => {
    editingId.value = null;
    Object.assign(form, { resourceType: 'article', title: '', url: '', status: 1 });
    knowledgeText.value = '';
    visible.value = true;
};
const openEdit = async (id) => {
    const d = await api.adminResourceDetail(id);
    editingId.value = id;
    Object.assign(form, d);
    knowledgeText.value = (d.knowledgePoints || []).join(',');
    visible.value = true;
};
const showDetail = async (id) => {
    detail.value = await api.adminResourceDetail(id);
    detailVisible.value = true;
};
const save = async () => {
    const payload = {
        ...form,
        knowledgePoints: knowledgeText.value.split(',').map((x) => x.trim()).filter(Boolean)
    };
    if (editingId.value)
        await api.adminUpdateResource(editingId.value, payload);
    else
        await api.adminCreateResource(payload);
    ElMessage.success('保存成功');
    visible.value = false;
    await load();
};
const remove = async (id) => {
    await api.adminDeleteResource(id);
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
    modelValue: (__VLS_ctx.q.resourceType),
    clearable: true,
}));
const __VLS_23 = __VLS_22({
    modelValue: (__VLS_ctx.q.resourceType),
    clearable: true,
}, ...__VLS_functionalComponentArgsRest(__VLS_22));
var __VLS_20;
const __VLS_25 = {}.ElFormItem;
/** @type {[typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, ]} */ ;
// @ts-ignore
const __VLS_26 = __VLS_asFunctionalComponent(__VLS_25, new __VLS_25({
    label: "状态",
}));
const __VLS_27 = __VLS_26({
    label: "状态",
}, ...__VLS_functionalComponentArgsRest(__VLS_26));
__VLS_28.slots.default;
const __VLS_29 = {}.ElSelect;
/** @type {[typeof __VLS_components.ElSelect, typeof __VLS_components.elSelect, typeof __VLS_components.ElSelect, typeof __VLS_components.elSelect, ]} */ ;
// @ts-ignore
const __VLS_30 = __VLS_asFunctionalComponent(__VLS_29, new __VLS_29({
    modelValue: (__VLS_ctx.q.status),
    clearable: true,
    ...{ style: {} },
}));
const __VLS_31 = __VLS_30({
    modelValue: (__VLS_ctx.q.status),
    clearable: true,
    ...{ style: {} },
}, ...__VLS_functionalComponentArgsRest(__VLS_30));
__VLS_32.slots.default;
const __VLS_33 = {}.ElOption;
/** @type {[typeof __VLS_components.ElOption, typeof __VLS_components.elOption, ]} */ ;
// @ts-ignore
const __VLS_34 = __VLS_asFunctionalComponent(__VLS_33, new __VLS_33({
    label: "启用",
    value: (1),
}));
const __VLS_35 = __VLS_34({
    label: "启用",
    value: (1),
}, ...__VLS_functionalComponentArgsRest(__VLS_34));
const __VLS_37 = {}.ElOption;
/** @type {[typeof __VLS_components.ElOption, typeof __VLS_components.elOption, ]} */ ;
// @ts-ignore
const __VLS_38 = __VLS_asFunctionalComponent(__VLS_37, new __VLS_37({
    label: "禁用",
    value: (0),
}));
const __VLS_39 = __VLS_38({
    label: "禁用",
    value: (0),
}, ...__VLS_functionalComponentArgsRest(__VLS_38));
var __VLS_32;
var __VLS_28;
const __VLS_41 = {}.ElButton;
/** @type {[typeof __VLS_components.ElButton, typeof __VLS_components.elButton, typeof __VLS_components.ElButton, typeof __VLS_components.elButton, ]} */ ;
// @ts-ignore
const __VLS_42 = __VLS_asFunctionalComponent(__VLS_41, new __VLS_41({
    ...{ 'onClick': {} },
    type: "primary",
}));
const __VLS_43 = __VLS_42({
    ...{ 'onClick': {} },
    type: "primary",
}, ...__VLS_functionalComponentArgsRest(__VLS_42));
let __VLS_45;
let __VLS_46;
let __VLS_47;
const __VLS_48 = {
    onClick: (__VLS_ctx.onSearch)
};
__VLS_44.slots.default;
var __VLS_44;
const __VLS_49 = {}.ElButton;
/** @type {[typeof __VLS_components.ElButton, typeof __VLS_components.elButton, typeof __VLS_components.ElButton, typeof __VLS_components.elButton, ]} */ ;
// @ts-ignore
const __VLS_50 = __VLS_asFunctionalComponent(__VLS_49, new __VLS_49({
    ...{ 'onClick': {} },
    type: "success",
}));
const __VLS_51 = __VLS_50({
    ...{ 'onClick': {} },
    type: "success",
}, ...__VLS_functionalComponentArgsRest(__VLS_50));
let __VLS_53;
let __VLS_54;
let __VLS_55;
const __VLS_56 = {
    onClick: (__VLS_ctx.openCreate)
};
__VLS_52.slots.default;
var __VLS_52;
var __VLS_8;
const __VLS_57 = {}.ElTable;
/** @type {[typeof __VLS_components.ElTable, typeof __VLS_components.elTable, typeof __VLS_components.ElTable, typeof __VLS_components.elTable, ]} */ ;
// @ts-ignore
const __VLS_58 = __VLS_asFunctionalComponent(__VLS_57, new __VLS_57({
    data: (__VLS_ctx.list),
}));
const __VLS_59 = __VLS_58({
    data: (__VLS_ctx.list),
}, ...__VLS_functionalComponentArgsRest(__VLS_58));
__VLS_60.slots.default;
const __VLS_61 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_62 = __VLS_asFunctionalComponent(__VLS_61, new __VLS_61({
    prop: "id",
    label: "ID",
    width: "90",
}));
const __VLS_63 = __VLS_62({
    prop: "id",
    label: "ID",
    width: "90",
}, ...__VLS_functionalComponentArgsRest(__VLS_62));
const __VLS_65 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_66 = __VLS_asFunctionalComponent(__VLS_65, new __VLS_65({
    prop: "resourceType",
    label: "类型",
    width: "120",
}));
const __VLS_67 = __VLS_66({
    prop: "resourceType",
    label: "类型",
    width: "120",
}, ...__VLS_functionalComponentArgsRest(__VLS_66));
const __VLS_69 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_70 = __VLS_asFunctionalComponent(__VLS_69, new __VLS_69({
    prop: "title",
    label: "标题",
}));
const __VLS_71 = __VLS_70({
    prop: "title",
    label: "标题",
}, ...__VLS_functionalComponentArgsRest(__VLS_70));
const __VLS_73 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_74 = __VLS_asFunctionalComponent(__VLS_73, new __VLS_73({
    prop: "url",
    label: "URL",
    showOverflowTooltip: true,
}));
const __VLS_75 = __VLS_74({
    prop: "url",
    label: "URL",
    showOverflowTooltip: true,
}, ...__VLS_functionalComponentArgsRest(__VLS_74));
const __VLS_77 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_78 = __VLS_asFunctionalComponent(__VLS_77, new __VLS_77({
    prop: "status",
    label: "状态",
    width: "100",
}));
const __VLS_79 = __VLS_78({
    prop: "status",
    label: "状态",
    width: "100",
}, ...__VLS_functionalComponentArgsRest(__VLS_78));
const __VLS_81 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_82 = __VLS_asFunctionalComponent(__VLS_81, new __VLS_81({
    label: "操作",
    width: "220",
}));
const __VLS_83 = __VLS_82({
    label: "操作",
    width: "220",
}, ...__VLS_functionalComponentArgsRest(__VLS_82));
__VLS_84.slots.default;
{
    const { default: __VLS_thisSlot } = __VLS_84.slots;
    const [scope] = __VLS_getSlotParams(__VLS_thisSlot);
    const __VLS_85 = {}.ElButton;
    /** @type {[typeof __VLS_components.ElButton, typeof __VLS_components.elButton, typeof __VLS_components.ElButton, typeof __VLS_components.elButton, ]} */ ;
    // @ts-ignore
    const __VLS_86 = __VLS_asFunctionalComponent(__VLS_85, new __VLS_85({
        ...{ 'onClick': {} },
        link: true,
        type: "primary",
    }));
    const __VLS_87 = __VLS_86({
        ...{ 'onClick': {} },
        link: true,
        type: "primary",
    }, ...__VLS_functionalComponentArgsRest(__VLS_86));
    let __VLS_89;
    let __VLS_90;
    let __VLS_91;
    const __VLS_92 = {
        onClick: (...[$event]) => {
            __VLS_ctx.openEdit(scope.row.id);
        }
    };
    __VLS_88.slots.default;
    var __VLS_88;
    const __VLS_93 = {}.ElButton;
    /** @type {[typeof __VLS_components.ElButton, typeof __VLS_components.elButton, typeof __VLS_components.ElButton, typeof __VLS_components.elButton, ]} */ ;
    // @ts-ignore
    const __VLS_94 = __VLS_asFunctionalComponent(__VLS_93, new __VLS_93({
        ...{ 'onClick': {} },
        link: true,
    }));
    const __VLS_95 = __VLS_94({
        ...{ 'onClick': {} },
        link: true,
    }, ...__VLS_functionalComponentArgsRest(__VLS_94));
    let __VLS_97;
    let __VLS_98;
    let __VLS_99;
    const __VLS_100 = {
        onClick: (...[$event]) => {
            __VLS_ctx.showDetail(scope.row.id);
        }
    };
    __VLS_96.slots.default;
    var __VLS_96;
    const __VLS_101 = {}.ElButton;
    /** @type {[typeof __VLS_components.ElButton, typeof __VLS_components.elButton, typeof __VLS_components.ElButton, typeof __VLS_components.elButton, ]} */ ;
    // @ts-ignore
    const __VLS_102 = __VLS_asFunctionalComponent(__VLS_101, new __VLS_101({
        ...{ 'onClick': {} },
        link: true,
        type: "danger",
    }));
    const __VLS_103 = __VLS_102({
        ...{ 'onClick': {} },
        link: true,
        type: "danger",
    }, ...__VLS_functionalComponentArgsRest(__VLS_102));
    let __VLS_105;
    let __VLS_106;
    let __VLS_107;
    const __VLS_108 = {
        onClick: (...[$event]) => {
            __VLS_ctx.remove(scope.row.id);
        }
    };
    __VLS_104.slots.default;
    var __VLS_104;
}
var __VLS_84;
var __VLS_60;
__VLS_asFunctionalElement(__VLS_intrinsicElements.div, __VLS_intrinsicElements.div)({
    ...{ style: {} },
});
const __VLS_109 = {}.ElPagination;
/** @type {[typeof __VLS_components.ElPagination, typeof __VLS_components.elPagination, ]} */ ;
// @ts-ignore
const __VLS_110 = __VLS_asFunctionalComponent(__VLS_109, new __VLS_109({
    ...{ 'onCurrentChange': {} },
    ...{ 'onSizeChange': {} },
    currentPage: (__VLS_ctx.q.page),
    pageSize: (__VLS_ctx.q.size),
    layout: "total, sizes, prev, pager, next, jumper",
    pageSizes: ([10, 20, 50]),
    total: (__VLS_ctx.total),
}));
const __VLS_111 = __VLS_110({
    ...{ 'onCurrentChange': {} },
    ...{ 'onSizeChange': {} },
    currentPage: (__VLS_ctx.q.page),
    pageSize: (__VLS_ctx.q.size),
    layout: "total, sizes, prev, pager, next, jumper",
    pageSizes: ([10, 20, 50]),
    total: (__VLS_ctx.total),
}, ...__VLS_functionalComponentArgsRest(__VLS_110));
let __VLS_113;
let __VLS_114;
let __VLS_115;
const __VLS_116 = {
    onCurrentChange: (__VLS_ctx.load)
};
const __VLS_117 = {
    onSizeChange: (__VLS_ctx.onSizeChange)
};
var __VLS_112;
const __VLS_118 = {}.ElDialog;
/** @type {[typeof __VLS_components.ElDialog, typeof __VLS_components.elDialog, typeof __VLS_components.ElDialog, typeof __VLS_components.elDialog, ]} */ ;
// @ts-ignore
const __VLS_119 = __VLS_asFunctionalComponent(__VLS_118, new __VLS_118({
    modelValue: (__VLS_ctx.visible),
    title: (__VLS_ctx.editingId ? '编辑资源' : '新增资源'),
    width: "760px",
}));
const __VLS_120 = __VLS_119({
    modelValue: (__VLS_ctx.visible),
    title: (__VLS_ctx.editingId ? '编辑资源' : '新增资源'),
    width: "760px",
}, ...__VLS_functionalComponentArgsRest(__VLS_119));
__VLS_121.slots.default;
const __VLS_122 = {}.ElForm;
/** @type {[typeof __VLS_components.ElForm, typeof __VLS_components.elForm, typeof __VLS_components.ElForm, typeof __VLS_components.elForm, ]} */ ;
// @ts-ignore
const __VLS_123 = __VLS_asFunctionalComponent(__VLS_122, new __VLS_122({
    labelWidth: "90px",
}));
const __VLS_124 = __VLS_123({
    labelWidth: "90px",
}, ...__VLS_functionalComponentArgsRest(__VLS_123));
__VLS_125.slots.default;
const __VLS_126 = {}.ElFormItem;
/** @type {[typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, ]} */ ;
// @ts-ignore
const __VLS_127 = __VLS_asFunctionalComponent(__VLS_126, new __VLS_126({
    label: "标题",
}));
const __VLS_128 = __VLS_127({
    label: "标题",
}, ...__VLS_functionalComponentArgsRest(__VLS_127));
__VLS_129.slots.default;
const __VLS_130 = {}.ElInput;
/** @type {[typeof __VLS_components.ElInput, typeof __VLS_components.elInput, ]} */ ;
// @ts-ignore
const __VLS_131 = __VLS_asFunctionalComponent(__VLS_130, new __VLS_130({
    modelValue: (__VLS_ctx.form.title),
}));
const __VLS_132 = __VLS_131({
    modelValue: (__VLS_ctx.form.title),
}, ...__VLS_functionalComponentArgsRest(__VLS_131));
var __VLS_129;
const __VLS_134 = {}.ElFormItem;
/** @type {[typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, ]} */ ;
// @ts-ignore
const __VLS_135 = __VLS_asFunctionalComponent(__VLS_134, new __VLS_134({
    label: "类型",
}));
const __VLS_136 = __VLS_135({
    label: "类型",
}, ...__VLS_functionalComponentArgsRest(__VLS_135));
__VLS_137.slots.default;
const __VLS_138 = {}.ElInput;
/** @type {[typeof __VLS_components.ElInput, typeof __VLS_components.elInput, ]} */ ;
// @ts-ignore
const __VLS_139 = __VLS_asFunctionalComponent(__VLS_138, new __VLS_138({
    modelValue: (__VLS_ctx.form.resourceType),
}));
const __VLS_140 = __VLS_139({
    modelValue: (__VLS_ctx.form.resourceType),
}, ...__VLS_functionalComponentArgsRest(__VLS_139));
var __VLS_137;
const __VLS_142 = {}.ElFormItem;
/** @type {[typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, ]} */ ;
// @ts-ignore
const __VLS_143 = __VLS_asFunctionalComponent(__VLS_142, new __VLS_142({
    label: "URL",
}));
const __VLS_144 = __VLS_143({
    label: "URL",
}, ...__VLS_functionalComponentArgsRest(__VLS_143));
__VLS_145.slots.default;
const __VLS_146 = {}.ElInput;
/** @type {[typeof __VLS_components.ElInput, typeof __VLS_components.elInput, ]} */ ;
// @ts-ignore
const __VLS_147 = __VLS_asFunctionalComponent(__VLS_146, new __VLS_146({
    modelValue: (__VLS_ctx.form.url),
}));
const __VLS_148 = __VLS_147({
    modelValue: (__VLS_ctx.form.url),
}, ...__VLS_functionalComponentArgsRest(__VLS_147));
var __VLS_145;
const __VLS_150 = {}.ElFormItem;
/** @type {[typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, ]} */ ;
// @ts-ignore
const __VLS_151 = __VLS_asFunctionalComponent(__VLS_150, new __VLS_150({
    label: "知识点",
}));
const __VLS_152 = __VLS_151({
    label: "知识点",
}, ...__VLS_functionalComponentArgsRest(__VLS_151));
__VLS_153.slots.default;
const __VLS_154 = {}.ElInput;
/** @type {[typeof __VLS_components.ElInput, typeof __VLS_components.elInput, ]} */ ;
// @ts-ignore
const __VLS_155 = __VLS_asFunctionalComponent(__VLS_154, new __VLS_154({
    modelValue: (__VLS_ctx.knowledgeText),
    placeholder: "逗号分隔",
}));
const __VLS_156 = __VLS_155({
    modelValue: (__VLS_ctx.knowledgeText),
    placeholder: "逗号分隔",
}, ...__VLS_functionalComponentArgsRest(__VLS_155));
var __VLS_153;
const __VLS_158 = {}.ElFormItem;
/** @type {[typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, ]} */ ;
// @ts-ignore
const __VLS_159 = __VLS_asFunctionalComponent(__VLS_158, new __VLS_158({
    label: "状态",
}));
const __VLS_160 = __VLS_159({
    label: "状态",
}, ...__VLS_functionalComponentArgsRest(__VLS_159));
__VLS_161.slots.default;
const __VLS_162 = {}.ElSelect;
/** @type {[typeof __VLS_components.ElSelect, typeof __VLS_components.elSelect, typeof __VLS_components.ElSelect, typeof __VLS_components.elSelect, ]} */ ;
// @ts-ignore
const __VLS_163 = __VLS_asFunctionalComponent(__VLS_162, new __VLS_162({
    modelValue: (__VLS_ctx.form.status),
}));
const __VLS_164 = __VLS_163({
    modelValue: (__VLS_ctx.form.status),
}, ...__VLS_functionalComponentArgsRest(__VLS_163));
__VLS_165.slots.default;
const __VLS_166 = {}.ElOption;
/** @type {[typeof __VLS_components.ElOption, typeof __VLS_components.elOption, ]} */ ;
// @ts-ignore
const __VLS_167 = __VLS_asFunctionalComponent(__VLS_166, new __VLS_166({
    label: "启用",
    value: (1),
}));
const __VLS_168 = __VLS_167({
    label: "启用",
    value: (1),
}, ...__VLS_functionalComponentArgsRest(__VLS_167));
const __VLS_170 = {}.ElOption;
/** @type {[typeof __VLS_components.ElOption, typeof __VLS_components.elOption, ]} */ ;
// @ts-ignore
const __VLS_171 = __VLS_asFunctionalComponent(__VLS_170, new __VLS_170({
    label: "禁用",
    value: (0),
}));
const __VLS_172 = __VLS_171({
    label: "禁用",
    value: (0),
}, ...__VLS_functionalComponentArgsRest(__VLS_171));
var __VLS_165;
var __VLS_161;
var __VLS_125;
{
    const { footer: __VLS_thisSlot } = __VLS_121.slots;
    const __VLS_174 = {}.ElButton;
    /** @type {[typeof __VLS_components.ElButton, typeof __VLS_components.elButton, typeof __VLS_components.ElButton, typeof __VLS_components.elButton, ]} */ ;
    // @ts-ignore
    const __VLS_175 = __VLS_asFunctionalComponent(__VLS_174, new __VLS_174({
        ...{ 'onClick': {} },
    }));
    const __VLS_176 = __VLS_175({
        ...{ 'onClick': {} },
    }, ...__VLS_functionalComponentArgsRest(__VLS_175));
    let __VLS_178;
    let __VLS_179;
    let __VLS_180;
    const __VLS_181 = {
        onClick: (...[$event]) => {
            __VLS_ctx.visible = false;
        }
    };
    __VLS_177.slots.default;
    var __VLS_177;
    const __VLS_182 = {}.ElButton;
    /** @type {[typeof __VLS_components.ElButton, typeof __VLS_components.elButton, typeof __VLS_components.ElButton, typeof __VLS_components.elButton, ]} */ ;
    // @ts-ignore
    const __VLS_183 = __VLS_asFunctionalComponent(__VLS_182, new __VLS_182({
        ...{ 'onClick': {} },
        type: "primary",
    }));
    const __VLS_184 = __VLS_183({
        ...{ 'onClick': {} },
        type: "primary",
    }, ...__VLS_functionalComponentArgsRest(__VLS_183));
    let __VLS_186;
    let __VLS_187;
    let __VLS_188;
    const __VLS_189 = {
        onClick: (__VLS_ctx.save)
    };
    __VLS_185.slots.default;
    var __VLS_185;
}
var __VLS_121;
const __VLS_190 = {}.ElDialog;
/** @type {[typeof __VLS_components.ElDialog, typeof __VLS_components.elDialog, typeof __VLS_components.ElDialog, typeof __VLS_components.elDialog, ]} */ ;
// @ts-ignore
const __VLS_191 = __VLS_asFunctionalComponent(__VLS_190, new __VLS_190({
    modelValue: (__VLS_ctx.detailVisible),
    title: "资源详情",
    width: "760px",
}));
const __VLS_192 = __VLS_191({
    modelValue: (__VLS_ctx.detailVisible),
    title: "资源详情",
    width: "760px",
}, ...__VLS_functionalComponentArgsRest(__VLS_191));
__VLS_193.slots.default;
__VLS_asFunctionalElement(__VLS_intrinsicElements.pre, __VLS_intrinsicElements.pre)({});
(JSON.stringify(__VLS_ctx.detail, null, 2));
var __VLS_193;
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
            knowledgeText: knowledgeText,
            detailVisible: detailVisible,
            detail: detail,
            load: load,
            onSearch: onSearch,
            onSizeChange: onSizeChange,
            openCreate: openCreate,
            openEdit: openEdit,
            showDetail: showDetail,
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
