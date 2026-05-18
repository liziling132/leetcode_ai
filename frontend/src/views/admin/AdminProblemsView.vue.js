import { onMounted, reactive, ref } from 'vue';
import { ElMessage } from 'element-plus';
import { api } from '@/api';
const q = reactive({ page: 1, size: 20, keyword: '', difficulty: '' });
const list = ref([]);
const total = ref(0);
const editVisible = ref(false);
const editingId = ref(null);
const form = reactive({ title: '', content: '', inputDesc: '', outputDesc: '', difficulty: 'EASY', status: 1 });
const languagesText = ref('java,python,cpp');
const knowledgeText = ref('array');
const testcaseVisible = ref(false);
const testcaseProblemId = ref(null);
const testcaseText = ref('[]');
const load = async () => {
    const data = await api.adminProblems(q);
    list.value = data.list || [];
    total.value = data.total || 0;
};
const onSearch = async () => { q.page = 1; await load(); };
const onSizeChange = async () => { q.page = 1; await load(); };
const openCreate = () => {
    editingId.value = null;
    Object.assign(form, { title: '', content: '', inputDesc: '', outputDesc: '', difficulty: 'EASY', status: 1 });
    languagesText.value = 'java,python,cpp';
    knowledgeText.value = 'array';
    editVisible.value = true;
};
const openEdit = async (id) => {
    const d = await api.adminProblemDetail(id);
    editingId.value = id;
    Object.assign(form, d);
    languagesText.value = (d.supportLanguages || []).join(',');
    knowledgeText.value = (d.knowledgePoints || []).join(',');
    editVisible.value = true;
};
const save = async () => {
    const payload = {
        ...form,
        supportLanguages: languagesText.value.split(',').map((s) => s.trim()).filter(Boolean),
        knowledgePoints: knowledgeText.value.split(',').map((s) => s.trim()).filter(Boolean)
    };
    if (editingId.value)
        await api.adminUpdateProblem(editingId.value, payload);
    else
        await api.adminCreateProblem(payload);
    ElMessage.success('保存成功');
    editVisible.value = false;
    await load();
};
const remove = async (id) => {
    await api.adminDeleteProblem(id);
    ElMessage.success('删除成功');
    await load();
};
const openTestcases = async (id) => {
    const d = await api.adminProblemDetail(id);
    testcaseProblemId.value = id;
    testcaseText.value = JSON.stringify((d.testcases || []).map((x) => ({ inputData: x.inputData, expectedOutput: x.expectedOutput, caseOrder: x.caseOrder })), null, 2);
    testcaseVisible.value = true;
};
const saveTestcases = async () => {
    if (!testcaseProblemId.value)
        return;
    let testcases = [];
    try {
        testcases = JSON.parse(testcaseText.value);
    }
    catch {
        ElMessage.error('测试用例 JSON 格式不正确');
        return;
    }
    await api.adminReplaceTestcases(testcaseProblemId.value, { testcases });
    ElMessage.success('测试用例已更新');
    testcaseVisible.value = false;
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
    label: "难度",
}));
const __VLS_19 = __VLS_18({
    label: "难度",
}, ...__VLS_functionalComponentArgsRest(__VLS_18));
__VLS_20.slots.default;
const __VLS_21 = {}.ElSelect;
/** @type {[typeof __VLS_components.ElSelect, typeof __VLS_components.elSelect, typeof __VLS_components.ElSelect, typeof __VLS_components.elSelect, ]} */ ;
// @ts-ignore
const __VLS_22 = __VLS_asFunctionalComponent(__VLS_21, new __VLS_21({
    modelValue: (__VLS_ctx.q.difficulty),
    clearable: true,
    ...{ style: {} },
}));
const __VLS_23 = __VLS_22({
    modelValue: (__VLS_ctx.q.difficulty),
    clearable: true,
    ...{ style: {} },
}, ...__VLS_functionalComponentArgsRest(__VLS_22));
__VLS_24.slots.default;
const __VLS_25 = {}.ElOption;
/** @type {[typeof __VLS_components.ElOption, typeof __VLS_components.elOption, ]} */ ;
// @ts-ignore
const __VLS_26 = __VLS_asFunctionalComponent(__VLS_25, new __VLS_25({
    label: "EASY",
    value: "EASY",
}));
const __VLS_27 = __VLS_26({
    label: "EASY",
    value: "EASY",
}, ...__VLS_functionalComponentArgsRest(__VLS_26));
const __VLS_29 = {}.ElOption;
/** @type {[typeof __VLS_components.ElOption, typeof __VLS_components.elOption, ]} */ ;
// @ts-ignore
const __VLS_30 = __VLS_asFunctionalComponent(__VLS_29, new __VLS_29({
    label: "MEDIUM",
    value: "MEDIUM",
}));
const __VLS_31 = __VLS_30({
    label: "MEDIUM",
    value: "MEDIUM",
}, ...__VLS_functionalComponentArgsRest(__VLS_30));
const __VLS_33 = {}.ElOption;
/** @type {[typeof __VLS_components.ElOption, typeof __VLS_components.elOption, ]} */ ;
// @ts-ignore
const __VLS_34 = __VLS_asFunctionalComponent(__VLS_33, new __VLS_33({
    label: "HARD",
    value: "HARD",
}));
const __VLS_35 = __VLS_34({
    label: "HARD",
    value: "HARD",
}, ...__VLS_functionalComponentArgsRest(__VLS_34));
var __VLS_24;
var __VLS_20;
const __VLS_37 = {}.ElButton;
/** @type {[typeof __VLS_components.ElButton, typeof __VLS_components.elButton, typeof __VLS_components.ElButton, typeof __VLS_components.elButton, ]} */ ;
// @ts-ignore
const __VLS_38 = __VLS_asFunctionalComponent(__VLS_37, new __VLS_37({
    ...{ 'onClick': {} },
    type: "primary",
}));
const __VLS_39 = __VLS_38({
    ...{ 'onClick': {} },
    type: "primary",
}, ...__VLS_functionalComponentArgsRest(__VLS_38));
let __VLS_41;
let __VLS_42;
let __VLS_43;
const __VLS_44 = {
    onClick: (__VLS_ctx.onSearch)
};
__VLS_40.slots.default;
var __VLS_40;
const __VLS_45 = {}.ElButton;
/** @type {[typeof __VLS_components.ElButton, typeof __VLS_components.elButton, typeof __VLS_components.ElButton, typeof __VLS_components.elButton, ]} */ ;
// @ts-ignore
const __VLS_46 = __VLS_asFunctionalComponent(__VLS_45, new __VLS_45({
    ...{ 'onClick': {} },
    type: "success",
}));
const __VLS_47 = __VLS_46({
    ...{ 'onClick': {} },
    type: "success",
}, ...__VLS_functionalComponentArgsRest(__VLS_46));
let __VLS_49;
let __VLS_50;
let __VLS_51;
const __VLS_52 = {
    onClick: (__VLS_ctx.openCreate)
};
__VLS_48.slots.default;
var __VLS_48;
var __VLS_8;
const __VLS_53 = {}.ElTable;
/** @type {[typeof __VLS_components.ElTable, typeof __VLS_components.elTable, typeof __VLS_components.ElTable, typeof __VLS_components.elTable, ]} */ ;
// @ts-ignore
const __VLS_54 = __VLS_asFunctionalComponent(__VLS_53, new __VLS_53({
    data: (__VLS_ctx.list),
}));
const __VLS_55 = __VLS_54({
    data: (__VLS_ctx.list),
}, ...__VLS_functionalComponentArgsRest(__VLS_54));
__VLS_56.slots.default;
const __VLS_57 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_58 = __VLS_asFunctionalComponent(__VLS_57, new __VLS_57({
    prop: "id",
    label: "ID",
    width: "90",
}));
const __VLS_59 = __VLS_58({
    prop: "id",
    label: "ID",
    width: "90",
}, ...__VLS_functionalComponentArgsRest(__VLS_58));
const __VLS_61 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_62 = __VLS_asFunctionalComponent(__VLS_61, new __VLS_61({
    prop: "title",
    label: "标题",
}));
const __VLS_63 = __VLS_62({
    prop: "title",
    label: "标题",
}, ...__VLS_functionalComponentArgsRest(__VLS_62));
const __VLS_65 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_66 = __VLS_asFunctionalComponent(__VLS_65, new __VLS_65({
    prop: "difficulty",
    label: "难度",
    width: "120",
}));
const __VLS_67 = __VLS_66({
    prop: "difficulty",
    label: "难度",
    width: "120",
}, ...__VLS_functionalComponentArgsRest(__VLS_66));
const __VLS_69 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_70 = __VLS_asFunctionalComponent(__VLS_69, new __VLS_69({
    prop: "status",
    label: "状态",
    width: "90",
}));
const __VLS_71 = __VLS_70({
    prop: "status",
    label: "状态",
    width: "90",
}, ...__VLS_functionalComponentArgsRest(__VLS_70));
const __VLS_73 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_74 = __VLS_asFunctionalComponent(__VLS_73, new __VLS_73({
    prop: "tagNames",
    label: "标签",
}));
const __VLS_75 = __VLS_74({
    prop: "tagNames",
    label: "标签",
}, ...__VLS_functionalComponentArgsRest(__VLS_74));
const __VLS_77 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_78 = __VLS_asFunctionalComponent(__VLS_77, new __VLS_77({
    label: "操作",
    width: "260",
}));
const __VLS_79 = __VLS_78({
    label: "操作",
    width: "260",
}, ...__VLS_functionalComponentArgsRest(__VLS_78));
__VLS_80.slots.default;
{
    const { default: __VLS_thisSlot } = __VLS_80.slots;
    const [scope] = __VLS_getSlotParams(__VLS_thisSlot);
    const __VLS_81 = {}.ElButton;
    /** @type {[typeof __VLS_components.ElButton, typeof __VLS_components.elButton, typeof __VLS_components.ElButton, typeof __VLS_components.elButton, ]} */ ;
    // @ts-ignore
    const __VLS_82 = __VLS_asFunctionalComponent(__VLS_81, new __VLS_81({
        ...{ 'onClick': {} },
        link: true,
        type: "primary",
    }));
    const __VLS_83 = __VLS_82({
        ...{ 'onClick': {} },
        link: true,
        type: "primary",
    }, ...__VLS_functionalComponentArgsRest(__VLS_82));
    let __VLS_85;
    let __VLS_86;
    let __VLS_87;
    const __VLS_88 = {
        onClick: (...[$event]) => {
            __VLS_ctx.openEdit(scope.row.id);
        }
    };
    __VLS_84.slots.default;
    var __VLS_84;
    const __VLS_89 = {}.ElButton;
    /** @type {[typeof __VLS_components.ElButton, typeof __VLS_components.elButton, typeof __VLS_components.ElButton, typeof __VLS_components.elButton, ]} */ ;
    // @ts-ignore
    const __VLS_90 = __VLS_asFunctionalComponent(__VLS_89, new __VLS_89({
        ...{ 'onClick': {} },
        link: true,
        type: "warning",
    }));
    const __VLS_91 = __VLS_90({
        ...{ 'onClick': {} },
        link: true,
        type: "warning",
    }, ...__VLS_functionalComponentArgsRest(__VLS_90));
    let __VLS_93;
    let __VLS_94;
    let __VLS_95;
    const __VLS_96 = {
        onClick: (...[$event]) => {
            __VLS_ctx.openTestcases(scope.row.id);
        }
    };
    __VLS_92.slots.default;
    var __VLS_92;
    const __VLS_97 = {}.ElButton;
    /** @type {[typeof __VLS_components.ElButton, typeof __VLS_components.elButton, typeof __VLS_components.ElButton, typeof __VLS_components.elButton, ]} */ ;
    // @ts-ignore
    const __VLS_98 = __VLS_asFunctionalComponent(__VLS_97, new __VLS_97({
        ...{ 'onClick': {} },
        link: true,
        type: "danger",
    }));
    const __VLS_99 = __VLS_98({
        ...{ 'onClick': {} },
        link: true,
        type: "danger",
    }, ...__VLS_functionalComponentArgsRest(__VLS_98));
    let __VLS_101;
    let __VLS_102;
    let __VLS_103;
    const __VLS_104 = {
        onClick: (...[$event]) => {
            __VLS_ctx.remove(scope.row.id);
        }
    };
    __VLS_100.slots.default;
    var __VLS_100;
}
var __VLS_80;
var __VLS_56;
__VLS_asFunctionalElement(__VLS_intrinsicElements.div, __VLS_intrinsicElements.div)({
    ...{ style: {} },
});
const __VLS_105 = {}.ElPagination;
/** @type {[typeof __VLS_components.ElPagination, typeof __VLS_components.elPagination, ]} */ ;
// @ts-ignore
const __VLS_106 = __VLS_asFunctionalComponent(__VLS_105, new __VLS_105({
    ...{ 'onCurrentChange': {} },
    ...{ 'onSizeChange': {} },
    currentPage: (__VLS_ctx.q.page),
    pageSize: (__VLS_ctx.q.size),
    layout: "total, sizes, prev, pager, next, jumper",
    pageSizes: ([10, 20, 50]),
    total: (__VLS_ctx.total),
}));
const __VLS_107 = __VLS_106({
    ...{ 'onCurrentChange': {} },
    ...{ 'onSizeChange': {} },
    currentPage: (__VLS_ctx.q.page),
    pageSize: (__VLS_ctx.q.size),
    layout: "total, sizes, prev, pager, next, jumper",
    pageSizes: ([10, 20, 50]),
    total: (__VLS_ctx.total),
}, ...__VLS_functionalComponentArgsRest(__VLS_106));
let __VLS_109;
let __VLS_110;
let __VLS_111;
const __VLS_112 = {
    onCurrentChange: (__VLS_ctx.load)
};
const __VLS_113 = {
    onSizeChange: (__VLS_ctx.onSizeChange)
};
var __VLS_108;
const __VLS_114 = {}.ElDialog;
/** @type {[typeof __VLS_components.ElDialog, typeof __VLS_components.elDialog, typeof __VLS_components.ElDialog, typeof __VLS_components.elDialog, ]} */ ;
// @ts-ignore
const __VLS_115 = __VLS_asFunctionalComponent(__VLS_114, new __VLS_114({
    modelValue: (__VLS_ctx.editVisible),
    title: (__VLS_ctx.editingId ? '编辑题目' : '新增题目'),
    width: "900px",
}));
const __VLS_116 = __VLS_115({
    modelValue: (__VLS_ctx.editVisible),
    title: (__VLS_ctx.editingId ? '编辑题目' : '新增题目'),
    width: "900px",
}, ...__VLS_functionalComponentArgsRest(__VLS_115));
__VLS_117.slots.default;
const __VLS_118 = {}.ElForm;
/** @type {[typeof __VLS_components.ElForm, typeof __VLS_components.elForm, typeof __VLS_components.ElForm, typeof __VLS_components.elForm, ]} */ ;
// @ts-ignore
const __VLS_119 = __VLS_asFunctionalComponent(__VLS_118, new __VLS_118({
    labelWidth: "120px",
}));
const __VLS_120 = __VLS_119({
    labelWidth: "120px",
}, ...__VLS_functionalComponentArgsRest(__VLS_119));
__VLS_121.slots.default;
const __VLS_122 = {}.ElFormItem;
/** @type {[typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, ]} */ ;
// @ts-ignore
const __VLS_123 = __VLS_asFunctionalComponent(__VLS_122, new __VLS_122({
    label: "标题",
}));
const __VLS_124 = __VLS_123({
    label: "标题",
}, ...__VLS_functionalComponentArgsRest(__VLS_123));
__VLS_125.slots.default;
const __VLS_126 = {}.ElInput;
/** @type {[typeof __VLS_components.ElInput, typeof __VLS_components.elInput, ]} */ ;
// @ts-ignore
const __VLS_127 = __VLS_asFunctionalComponent(__VLS_126, new __VLS_126({
    modelValue: (__VLS_ctx.form.title),
}));
const __VLS_128 = __VLS_127({
    modelValue: (__VLS_ctx.form.title),
}, ...__VLS_functionalComponentArgsRest(__VLS_127));
var __VLS_125;
const __VLS_130 = {}.ElFormItem;
/** @type {[typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, ]} */ ;
// @ts-ignore
const __VLS_131 = __VLS_asFunctionalComponent(__VLS_130, new __VLS_130({
    label: "难度",
}));
const __VLS_132 = __VLS_131({
    label: "难度",
}, ...__VLS_functionalComponentArgsRest(__VLS_131));
__VLS_133.slots.default;
const __VLS_134 = {}.ElSelect;
/** @type {[typeof __VLS_components.ElSelect, typeof __VLS_components.elSelect, typeof __VLS_components.ElSelect, typeof __VLS_components.elSelect, ]} */ ;
// @ts-ignore
const __VLS_135 = __VLS_asFunctionalComponent(__VLS_134, new __VLS_134({
    modelValue: (__VLS_ctx.form.difficulty),
}));
const __VLS_136 = __VLS_135({
    modelValue: (__VLS_ctx.form.difficulty),
}, ...__VLS_functionalComponentArgsRest(__VLS_135));
__VLS_137.slots.default;
const __VLS_138 = {}.ElOption;
/** @type {[typeof __VLS_components.ElOption, typeof __VLS_components.elOption, ]} */ ;
// @ts-ignore
const __VLS_139 = __VLS_asFunctionalComponent(__VLS_138, new __VLS_138({
    label: "EASY",
    value: "EASY",
}));
const __VLS_140 = __VLS_139({
    label: "EASY",
    value: "EASY",
}, ...__VLS_functionalComponentArgsRest(__VLS_139));
const __VLS_142 = {}.ElOption;
/** @type {[typeof __VLS_components.ElOption, typeof __VLS_components.elOption, ]} */ ;
// @ts-ignore
const __VLS_143 = __VLS_asFunctionalComponent(__VLS_142, new __VLS_142({
    label: "MEDIUM",
    value: "MEDIUM",
}));
const __VLS_144 = __VLS_143({
    label: "MEDIUM",
    value: "MEDIUM",
}, ...__VLS_functionalComponentArgsRest(__VLS_143));
const __VLS_146 = {}.ElOption;
/** @type {[typeof __VLS_components.ElOption, typeof __VLS_components.elOption, ]} */ ;
// @ts-ignore
const __VLS_147 = __VLS_asFunctionalComponent(__VLS_146, new __VLS_146({
    label: "HARD",
    value: "HARD",
}));
const __VLS_148 = __VLS_147({
    label: "HARD",
    value: "HARD",
}, ...__VLS_functionalComponentArgsRest(__VLS_147));
var __VLS_137;
var __VLS_133;
const __VLS_150 = {}.ElFormItem;
/** @type {[typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, ]} */ ;
// @ts-ignore
const __VLS_151 = __VLS_asFunctionalComponent(__VLS_150, new __VLS_150({
    label: "状态",
}));
const __VLS_152 = __VLS_151({
    label: "状态",
}, ...__VLS_functionalComponentArgsRest(__VLS_151));
__VLS_153.slots.default;
const __VLS_154 = {}.ElSelect;
/** @type {[typeof __VLS_components.ElSelect, typeof __VLS_components.elSelect, typeof __VLS_components.ElSelect, typeof __VLS_components.elSelect, ]} */ ;
// @ts-ignore
const __VLS_155 = __VLS_asFunctionalComponent(__VLS_154, new __VLS_154({
    modelValue: (__VLS_ctx.form.status),
}));
const __VLS_156 = __VLS_155({
    modelValue: (__VLS_ctx.form.status),
}, ...__VLS_functionalComponentArgsRest(__VLS_155));
__VLS_157.slots.default;
const __VLS_158 = {}.ElOption;
/** @type {[typeof __VLS_components.ElOption, typeof __VLS_components.elOption, ]} */ ;
// @ts-ignore
const __VLS_159 = __VLS_asFunctionalComponent(__VLS_158, new __VLS_158({
    label: "启用",
    value: (1),
}));
const __VLS_160 = __VLS_159({
    label: "启用",
    value: (1),
}, ...__VLS_functionalComponentArgsRest(__VLS_159));
const __VLS_162 = {}.ElOption;
/** @type {[typeof __VLS_components.ElOption, typeof __VLS_components.elOption, ]} */ ;
// @ts-ignore
const __VLS_163 = __VLS_asFunctionalComponent(__VLS_162, new __VLS_162({
    label: "禁用",
    value: (0),
}));
const __VLS_164 = __VLS_163({
    label: "禁用",
    value: (0),
}, ...__VLS_functionalComponentArgsRest(__VLS_163));
var __VLS_157;
var __VLS_153;
const __VLS_166 = {}.ElFormItem;
/** @type {[typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, ]} */ ;
// @ts-ignore
const __VLS_167 = __VLS_asFunctionalComponent(__VLS_166, new __VLS_166({
    label: "支持语言",
}));
const __VLS_168 = __VLS_167({
    label: "支持语言",
}, ...__VLS_functionalComponentArgsRest(__VLS_167));
__VLS_169.slots.default;
const __VLS_170 = {}.ElInput;
/** @type {[typeof __VLS_components.ElInput, typeof __VLS_components.elInput, ]} */ ;
// @ts-ignore
const __VLS_171 = __VLS_asFunctionalComponent(__VLS_170, new __VLS_170({
    modelValue: (__VLS_ctx.languagesText),
    placeholder: "逗号分隔，例如 java,python,cpp",
}));
const __VLS_172 = __VLS_171({
    modelValue: (__VLS_ctx.languagesText),
    placeholder: "逗号分隔，例如 java,python,cpp",
}, ...__VLS_functionalComponentArgsRest(__VLS_171));
var __VLS_169;
const __VLS_174 = {}.ElFormItem;
/** @type {[typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, ]} */ ;
// @ts-ignore
const __VLS_175 = __VLS_asFunctionalComponent(__VLS_174, new __VLS_174({
    label: "知识点",
}));
const __VLS_176 = __VLS_175({
    label: "知识点",
}, ...__VLS_functionalComponentArgsRest(__VLS_175));
__VLS_177.slots.default;
const __VLS_178 = {}.ElInput;
/** @type {[typeof __VLS_components.ElInput, typeof __VLS_components.elInput, ]} */ ;
// @ts-ignore
const __VLS_179 = __VLS_asFunctionalComponent(__VLS_178, new __VLS_178({
    modelValue: (__VLS_ctx.knowledgeText),
    placeholder: "逗号分隔",
}));
const __VLS_180 = __VLS_179({
    modelValue: (__VLS_ctx.knowledgeText),
    placeholder: "逗号分隔",
}, ...__VLS_functionalComponentArgsRest(__VLS_179));
var __VLS_177;
const __VLS_182 = {}.ElFormItem;
/** @type {[typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, ]} */ ;
// @ts-ignore
const __VLS_183 = __VLS_asFunctionalComponent(__VLS_182, new __VLS_182({
    label: "题目描述",
}));
const __VLS_184 = __VLS_183({
    label: "题目描述",
}, ...__VLS_functionalComponentArgsRest(__VLS_183));
__VLS_185.slots.default;
const __VLS_186 = {}.ElInput;
/** @type {[typeof __VLS_components.ElInput, typeof __VLS_components.elInput, ]} */ ;
// @ts-ignore
const __VLS_187 = __VLS_asFunctionalComponent(__VLS_186, new __VLS_186({
    modelValue: (__VLS_ctx.form.content),
    type: "textarea",
    rows: (4),
}));
const __VLS_188 = __VLS_187({
    modelValue: (__VLS_ctx.form.content),
    type: "textarea",
    rows: (4),
}, ...__VLS_functionalComponentArgsRest(__VLS_187));
var __VLS_185;
const __VLS_190 = {}.ElFormItem;
/** @type {[typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, ]} */ ;
// @ts-ignore
const __VLS_191 = __VLS_asFunctionalComponent(__VLS_190, new __VLS_190({
    label: "输入说明",
}));
const __VLS_192 = __VLS_191({
    label: "输入说明",
}, ...__VLS_functionalComponentArgsRest(__VLS_191));
__VLS_193.slots.default;
const __VLS_194 = {}.ElInput;
/** @type {[typeof __VLS_components.ElInput, typeof __VLS_components.elInput, ]} */ ;
// @ts-ignore
const __VLS_195 = __VLS_asFunctionalComponent(__VLS_194, new __VLS_194({
    modelValue: (__VLS_ctx.form.inputDesc),
    type: "textarea",
    rows: (2),
}));
const __VLS_196 = __VLS_195({
    modelValue: (__VLS_ctx.form.inputDesc),
    type: "textarea",
    rows: (2),
}, ...__VLS_functionalComponentArgsRest(__VLS_195));
var __VLS_193;
const __VLS_198 = {}.ElFormItem;
/** @type {[typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, ]} */ ;
// @ts-ignore
const __VLS_199 = __VLS_asFunctionalComponent(__VLS_198, new __VLS_198({
    label: "输出说明",
}));
const __VLS_200 = __VLS_199({
    label: "输出说明",
}, ...__VLS_functionalComponentArgsRest(__VLS_199));
__VLS_201.slots.default;
const __VLS_202 = {}.ElInput;
/** @type {[typeof __VLS_components.ElInput, typeof __VLS_components.elInput, ]} */ ;
// @ts-ignore
const __VLS_203 = __VLS_asFunctionalComponent(__VLS_202, new __VLS_202({
    modelValue: (__VLS_ctx.form.outputDesc),
    type: "textarea",
    rows: (2),
}));
const __VLS_204 = __VLS_203({
    modelValue: (__VLS_ctx.form.outputDesc),
    type: "textarea",
    rows: (2),
}, ...__VLS_functionalComponentArgsRest(__VLS_203));
var __VLS_201;
var __VLS_121;
{
    const { footer: __VLS_thisSlot } = __VLS_117.slots;
    const __VLS_206 = {}.ElButton;
    /** @type {[typeof __VLS_components.ElButton, typeof __VLS_components.elButton, typeof __VLS_components.ElButton, typeof __VLS_components.elButton, ]} */ ;
    // @ts-ignore
    const __VLS_207 = __VLS_asFunctionalComponent(__VLS_206, new __VLS_206({
        ...{ 'onClick': {} },
    }));
    const __VLS_208 = __VLS_207({
        ...{ 'onClick': {} },
    }, ...__VLS_functionalComponentArgsRest(__VLS_207));
    let __VLS_210;
    let __VLS_211;
    let __VLS_212;
    const __VLS_213 = {
        onClick: (...[$event]) => {
            __VLS_ctx.editVisible = false;
        }
    };
    __VLS_209.slots.default;
    var __VLS_209;
    const __VLS_214 = {}.ElButton;
    /** @type {[typeof __VLS_components.ElButton, typeof __VLS_components.elButton, typeof __VLS_components.ElButton, typeof __VLS_components.elButton, ]} */ ;
    // @ts-ignore
    const __VLS_215 = __VLS_asFunctionalComponent(__VLS_214, new __VLS_214({
        ...{ 'onClick': {} },
        type: "primary",
    }));
    const __VLS_216 = __VLS_215({
        ...{ 'onClick': {} },
        type: "primary",
    }, ...__VLS_functionalComponentArgsRest(__VLS_215));
    let __VLS_218;
    let __VLS_219;
    let __VLS_220;
    const __VLS_221 = {
        onClick: (__VLS_ctx.save)
    };
    __VLS_217.slots.default;
    var __VLS_217;
}
var __VLS_117;
const __VLS_222 = {}.ElDialog;
/** @type {[typeof __VLS_components.ElDialog, typeof __VLS_components.elDialog, typeof __VLS_components.ElDialog, typeof __VLS_components.elDialog, ]} */ ;
// @ts-ignore
const __VLS_223 = __VLS_asFunctionalComponent(__VLS_222, new __VLS_222({
    modelValue: (__VLS_ctx.testcaseVisible),
    title: "替换测试用例",
    width: "900px",
}));
const __VLS_224 = __VLS_223({
    modelValue: (__VLS_ctx.testcaseVisible),
    title: "替换测试用例",
    width: "900px",
}, ...__VLS_functionalComponentArgsRest(__VLS_223));
__VLS_225.slots.default;
const __VLS_226 = {}.ElInput;
/** @type {[typeof __VLS_components.ElInput, typeof __VLS_components.elInput, ]} */ ;
// @ts-ignore
const __VLS_227 = __VLS_asFunctionalComponent(__VLS_226, new __VLS_226({
    modelValue: (__VLS_ctx.testcaseText),
    type: "textarea",
    rows: (12),
    placeholder: 'JSON数组，例如[{"inputData":"1 2","expectedOutput":"3","caseOrder":1}]',
}));
const __VLS_228 = __VLS_227({
    modelValue: (__VLS_ctx.testcaseText),
    type: "textarea",
    rows: (12),
    placeholder: 'JSON数组，例如[{"inputData":"1 2","expectedOutput":"3","caseOrder":1}]',
}, ...__VLS_functionalComponentArgsRest(__VLS_227));
{
    const { footer: __VLS_thisSlot } = __VLS_225.slots;
    const __VLS_230 = {}.ElButton;
    /** @type {[typeof __VLS_components.ElButton, typeof __VLS_components.elButton, typeof __VLS_components.ElButton, typeof __VLS_components.elButton, ]} */ ;
    // @ts-ignore
    const __VLS_231 = __VLS_asFunctionalComponent(__VLS_230, new __VLS_230({
        ...{ 'onClick': {} },
    }));
    const __VLS_232 = __VLS_231({
        ...{ 'onClick': {} },
    }, ...__VLS_functionalComponentArgsRest(__VLS_231));
    let __VLS_234;
    let __VLS_235;
    let __VLS_236;
    const __VLS_237 = {
        onClick: (...[$event]) => {
            __VLS_ctx.testcaseVisible = false;
        }
    };
    __VLS_233.slots.default;
    var __VLS_233;
    const __VLS_238 = {}.ElButton;
    /** @type {[typeof __VLS_components.ElButton, typeof __VLS_components.elButton, typeof __VLS_components.ElButton, typeof __VLS_components.elButton, ]} */ ;
    // @ts-ignore
    const __VLS_239 = __VLS_asFunctionalComponent(__VLS_238, new __VLS_238({
        ...{ 'onClick': {} },
        type: "primary",
    }));
    const __VLS_240 = __VLS_239({
        ...{ 'onClick': {} },
        type: "primary",
    }, ...__VLS_functionalComponentArgsRest(__VLS_239));
    let __VLS_242;
    let __VLS_243;
    let __VLS_244;
    const __VLS_245 = {
        onClick: (__VLS_ctx.saveTestcases)
    };
    __VLS_241.slots.default;
    var __VLS_241;
}
var __VLS_225;
var __VLS_3;
var __VLS_dollars;
const __VLS_self = (await import('vue')).defineComponent({
    setup() {
        return {
            q: q,
            list: list,
            total: total,
            editVisible: editVisible,
            editingId: editingId,
            form: form,
            languagesText: languagesText,
            knowledgeText: knowledgeText,
            testcaseVisible: testcaseVisible,
            testcaseText: testcaseText,
            load: load,
            onSearch: onSearch,
            onSizeChange: onSizeChange,
            openCreate: openCreate,
            openEdit: openEdit,
            save: save,
            remove: remove,
            openTestcases: openTestcases,
            saveTestcases: saveTestcases,
        };
    },
});
export default (await import('vue')).defineComponent({
    setup() {
        return {};
    },
});
; /* PartiallyEnd: #4569/main.vue */
