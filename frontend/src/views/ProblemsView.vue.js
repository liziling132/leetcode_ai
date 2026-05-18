import { onMounted, reactive, ref } from 'vue';
import { ElMessage } from 'element-plus';
import { api } from '@/api';
const q = reactive({ page: 1, size: 20, keyword: '', difficulty: '', language: '', tagId: null });
const list = ref([]);
const total = ref(0);
const favSet = ref(new Set());
const loadFavorites = async () => {
    const data = await api.favorites({ page: 1, size: 500 });
    favSet.value = new Set((data.list || []).map((x) => Number(x.problemId)));
};
const load = async () => {
    const data = await api.problems(q);
    list.value = data.list || [];
    total.value = data.total || 0;
};
const onSearch = async () => {
    q.page = 1;
    await load();
};
const onSizeChange = async () => {
    q.page = 1;
    await load();
};
const isFav = (id) => favSet.value.has(Number(id));
const toggleFav = async (id) => {
    try {
        if (isFav(id)) {
            await api.unfavorite(id);
            favSet.value.delete(Number(id));
            ElMessage.success('已取消收藏');
        }
        else {
            await api.favorite(id);
            favSet.value.add(Number(id));
            ElMessage.success('收藏成功');
        }
    }
    catch (e) {
        ElMessage.error(e?.message || '操作失败');
    }
};
onMounted(async () => {
    await Promise.all([load(), loadFavorites()]);
});
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
    ...{ 'onSubmit': {} },
    inline: true,
}));
const __VLS_7 = __VLS_6({
    ...{ 'onSubmit': {} },
    inline: true,
}, ...__VLS_functionalComponentArgsRest(__VLS_6));
let __VLS_9;
let __VLS_10;
let __VLS_11;
const __VLS_12 = {
    onSubmit: () => { }
};
__VLS_8.slots.default;
const __VLS_13 = {}.ElFormItem;
/** @type {[typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, ]} */ ;
// @ts-ignore
const __VLS_14 = __VLS_asFunctionalComponent(__VLS_13, new __VLS_13({
    label: "关键字",
}));
const __VLS_15 = __VLS_14({
    label: "关键字",
}, ...__VLS_functionalComponentArgsRest(__VLS_14));
__VLS_16.slots.default;
const __VLS_17 = {}.ElInput;
/** @type {[typeof __VLS_components.ElInput, typeof __VLS_components.elInput, ]} */ ;
// @ts-ignore
const __VLS_18 = __VLS_asFunctionalComponent(__VLS_17, new __VLS_17({
    modelValue: (__VLS_ctx.q.keyword),
    clearable: true,
}));
const __VLS_19 = __VLS_18({
    modelValue: (__VLS_ctx.q.keyword),
    clearable: true,
}, ...__VLS_functionalComponentArgsRest(__VLS_18));
var __VLS_16;
const __VLS_21 = {}.ElFormItem;
/** @type {[typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, ]} */ ;
// @ts-ignore
const __VLS_22 = __VLS_asFunctionalComponent(__VLS_21, new __VLS_21({
    label: "难度",
}));
const __VLS_23 = __VLS_22({
    label: "难度",
}, ...__VLS_functionalComponentArgsRest(__VLS_22));
__VLS_24.slots.default;
const __VLS_25 = {}.ElSelect;
/** @type {[typeof __VLS_components.ElSelect, typeof __VLS_components.elSelect, typeof __VLS_components.ElSelect, typeof __VLS_components.elSelect, ]} */ ;
// @ts-ignore
const __VLS_26 = __VLS_asFunctionalComponent(__VLS_25, new __VLS_25({
    modelValue: (__VLS_ctx.q.difficulty),
    clearable: true,
    ...{ style: {} },
}));
const __VLS_27 = __VLS_26({
    modelValue: (__VLS_ctx.q.difficulty),
    clearable: true,
    ...{ style: {} },
}, ...__VLS_functionalComponentArgsRest(__VLS_26));
__VLS_28.slots.default;
const __VLS_29 = {}.ElOption;
/** @type {[typeof __VLS_components.ElOption, typeof __VLS_components.elOption, ]} */ ;
// @ts-ignore
const __VLS_30 = __VLS_asFunctionalComponent(__VLS_29, new __VLS_29({
    label: "EASY",
    value: "EASY",
}));
const __VLS_31 = __VLS_30({
    label: "EASY",
    value: "EASY",
}, ...__VLS_functionalComponentArgsRest(__VLS_30));
const __VLS_33 = {}.ElOption;
/** @type {[typeof __VLS_components.ElOption, typeof __VLS_components.elOption, ]} */ ;
// @ts-ignore
const __VLS_34 = __VLS_asFunctionalComponent(__VLS_33, new __VLS_33({
    label: "MEDIUM",
    value: "MEDIUM",
}));
const __VLS_35 = __VLS_34({
    label: "MEDIUM",
    value: "MEDIUM",
}, ...__VLS_functionalComponentArgsRest(__VLS_34));
const __VLS_37 = {}.ElOption;
/** @type {[typeof __VLS_components.ElOption, typeof __VLS_components.elOption, ]} */ ;
// @ts-ignore
const __VLS_38 = __VLS_asFunctionalComponent(__VLS_37, new __VLS_37({
    label: "HARD",
    value: "HARD",
}));
const __VLS_39 = __VLS_38({
    label: "HARD",
    value: "HARD",
}, ...__VLS_functionalComponentArgsRest(__VLS_38));
var __VLS_28;
var __VLS_24;
const __VLS_41 = {}.ElFormItem;
/** @type {[typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, ]} */ ;
// @ts-ignore
const __VLS_42 = __VLS_asFunctionalComponent(__VLS_41, new __VLS_41({
    label: "语言",
}));
const __VLS_43 = __VLS_42({
    label: "语言",
}, ...__VLS_functionalComponentArgsRest(__VLS_42));
__VLS_44.slots.default;
const __VLS_45 = {}.ElSelect;
/** @type {[typeof __VLS_components.ElSelect, typeof __VLS_components.elSelect, typeof __VLS_components.ElSelect, typeof __VLS_components.elSelect, ]} */ ;
// @ts-ignore
const __VLS_46 = __VLS_asFunctionalComponent(__VLS_45, new __VLS_45({
    modelValue: (__VLS_ctx.q.language),
    clearable: true,
    ...{ style: {} },
}));
const __VLS_47 = __VLS_46({
    modelValue: (__VLS_ctx.q.language),
    clearable: true,
    ...{ style: {} },
}, ...__VLS_functionalComponentArgsRest(__VLS_46));
__VLS_48.slots.default;
const __VLS_49 = {}.ElOption;
/** @type {[typeof __VLS_components.ElOption, typeof __VLS_components.elOption, ]} */ ;
// @ts-ignore
const __VLS_50 = __VLS_asFunctionalComponent(__VLS_49, new __VLS_49({
    label: "java",
    value: "java",
}));
const __VLS_51 = __VLS_50({
    label: "java",
    value: "java",
}, ...__VLS_functionalComponentArgsRest(__VLS_50));
const __VLS_53 = {}.ElOption;
/** @type {[typeof __VLS_components.ElOption, typeof __VLS_components.elOption, ]} */ ;
// @ts-ignore
const __VLS_54 = __VLS_asFunctionalComponent(__VLS_53, new __VLS_53({
    label: "python",
    value: "python",
}));
const __VLS_55 = __VLS_54({
    label: "python",
    value: "python",
}, ...__VLS_functionalComponentArgsRest(__VLS_54));
const __VLS_57 = {}.ElOption;
/** @type {[typeof __VLS_components.ElOption, typeof __VLS_components.elOption, ]} */ ;
// @ts-ignore
const __VLS_58 = __VLS_asFunctionalComponent(__VLS_57, new __VLS_57({
    label: "cpp",
    value: "cpp",
}));
const __VLS_59 = __VLS_58({
    label: "cpp",
    value: "cpp",
}, ...__VLS_functionalComponentArgsRest(__VLS_58));
var __VLS_48;
var __VLS_44;
const __VLS_61 = {}.ElFormItem;
/** @type {[typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, typeof __VLS_components.ElFormItem, typeof __VLS_components.elFormItem, ]} */ ;
// @ts-ignore
const __VLS_62 = __VLS_asFunctionalComponent(__VLS_61, new __VLS_61({
    label: "标签ID",
}));
const __VLS_63 = __VLS_62({
    label: "标签ID",
}, ...__VLS_functionalComponentArgsRest(__VLS_62));
__VLS_64.slots.default;
const __VLS_65 = {}.ElInputNumber;
/** @type {[typeof __VLS_components.ElInputNumber, typeof __VLS_components.elInputNumber, ]} */ ;
// @ts-ignore
const __VLS_66 = __VLS_asFunctionalComponent(__VLS_65, new __VLS_65({
    modelValue: (__VLS_ctx.q.tagId),
    min: (1),
    controlsPosition: "right",
}));
const __VLS_67 = __VLS_66({
    modelValue: (__VLS_ctx.q.tagId),
    min: (1),
    controlsPosition: "right",
}, ...__VLS_functionalComponentArgsRest(__VLS_66));
var __VLS_64;
const __VLS_69 = {}.ElButton;
/** @type {[typeof __VLS_components.ElButton, typeof __VLS_components.elButton, typeof __VLS_components.ElButton, typeof __VLS_components.elButton, ]} */ ;
// @ts-ignore
const __VLS_70 = __VLS_asFunctionalComponent(__VLS_69, new __VLS_69({
    ...{ 'onClick': {} },
    type: "primary",
}));
const __VLS_71 = __VLS_70({
    ...{ 'onClick': {} },
    type: "primary",
}, ...__VLS_functionalComponentArgsRest(__VLS_70));
let __VLS_73;
let __VLS_74;
let __VLS_75;
const __VLS_76 = {
    onClick: (__VLS_ctx.onSearch)
};
__VLS_72.slots.default;
var __VLS_72;
var __VLS_8;
const __VLS_77 = {}.ElTable;
/** @type {[typeof __VLS_components.ElTable, typeof __VLS_components.elTable, typeof __VLS_components.ElTable, typeof __VLS_components.elTable, ]} */ ;
// @ts-ignore
const __VLS_78 = __VLS_asFunctionalComponent(__VLS_77, new __VLS_77({
    data: (__VLS_ctx.list),
    ...{ style: {} },
}));
const __VLS_79 = __VLS_78({
    data: (__VLS_ctx.list),
    ...{ style: {} },
}, ...__VLS_functionalComponentArgsRest(__VLS_78));
__VLS_80.slots.default;
const __VLS_81 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_82 = __VLS_asFunctionalComponent(__VLS_81, new __VLS_81({
    prop: "id",
    label: "ID",
    width: "80",
}));
const __VLS_83 = __VLS_82({
    prop: "id",
    label: "ID",
    width: "80",
}, ...__VLS_functionalComponentArgsRest(__VLS_82));
const __VLS_85 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_86 = __VLS_asFunctionalComponent(__VLS_85, new __VLS_85({
    prop: "title",
    label: "题目",
}));
const __VLS_87 = __VLS_86({
    prop: "title",
    label: "题目",
}, ...__VLS_functionalComponentArgsRest(__VLS_86));
const __VLS_89 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_90 = __VLS_asFunctionalComponent(__VLS_89, new __VLS_89({
    prop: "difficulty",
    label: "难度",
    width: "120",
}));
const __VLS_91 = __VLS_90({
    prop: "difficulty",
    label: "难度",
    width: "120",
}, ...__VLS_functionalComponentArgsRest(__VLS_90));
const __VLS_93 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_94 = __VLS_asFunctionalComponent(__VLS_93, new __VLS_93({
    prop: "tags",
    label: "标签",
    width: "220",
}));
const __VLS_95 = __VLS_94({
    prop: "tags",
    label: "标签",
    width: "220",
}, ...__VLS_functionalComponentArgsRest(__VLS_94));
__VLS_96.slots.default;
{
    const { default: __VLS_thisSlot } = __VLS_96.slots;
    const [scope] = __VLS_getSlotParams(__VLS_thisSlot);
    ((scope.row.tags || []).join(' / '));
}
var __VLS_96;
const __VLS_97 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_98 = __VLS_asFunctionalComponent(__VLS_97, new __VLS_97({
    label: "收藏",
    width: "100",
}));
const __VLS_99 = __VLS_98({
    label: "收藏",
    width: "100",
}, ...__VLS_functionalComponentArgsRest(__VLS_98));
__VLS_100.slots.default;
{
    const { default: __VLS_thisSlot } = __VLS_100.slots;
    const [scope] = __VLS_getSlotParams(__VLS_thisSlot);
    const __VLS_101 = {}.ElButton;
    /** @type {[typeof __VLS_components.ElButton, typeof __VLS_components.elButton, typeof __VLS_components.ElButton, typeof __VLS_components.elButton, ]} */ ;
    // @ts-ignore
    const __VLS_102 = __VLS_asFunctionalComponent(__VLS_101, new __VLS_101({
        ...{ 'onClick': {} },
        link: true,
        type: (__VLS_ctx.isFav(scope.row.id) ? 'warning' : 'primary'),
    }));
    const __VLS_103 = __VLS_102({
        ...{ 'onClick': {} },
        link: true,
        type: (__VLS_ctx.isFav(scope.row.id) ? 'warning' : 'primary'),
    }, ...__VLS_functionalComponentArgsRest(__VLS_102));
    let __VLS_105;
    let __VLS_106;
    let __VLS_107;
    const __VLS_108 = {
        onClick: (...[$event]) => {
            __VLS_ctx.toggleFav(scope.row.id);
        }
    };
    __VLS_104.slots.default;
    (__VLS_ctx.isFav(scope.row.id) ? '已收藏' : '收藏');
    var __VLS_104;
}
var __VLS_100;
const __VLS_109 = {}.ElTableColumn;
/** @type {[typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, typeof __VLS_components.ElTableColumn, typeof __VLS_components.elTableColumn, ]} */ ;
// @ts-ignore
const __VLS_110 = __VLS_asFunctionalComponent(__VLS_109, new __VLS_109({
    label: "操作",
    width: "120",
}));
const __VLS_111 = __VLS_110({
    label: "操作",
    width: "120",
}, ...__VLS_functionalComponentArgsRest(__VLS_110));
__VLS_112.slots.default;
{
    const { default: __VLS_thisSlot } = __VLS_112.slots;
    const [scope] = __VLS_getSlotParams(__VLS_thisSlot);
    const __VLS_113 = {}.ElButton;
    /** @type {[typeof __VLS_components.ElButton, typeof __VLS_components.elButton, typeof __VLS_components.ElButton, typeof __VLS_components.elButton, ]} */ ;
    // @ts-ignore
    const __VLS_114 = __VLS_asFunctionalComponent(__VLS_113, new __VLS_113({
        ...{ 'onClick': {} },
        link: true,
        type: "primary",
    }));
    const __VLS_115 = __VLS_114({
        ...{ 'onClick': {} },
        link: true,
        type: "primary",
    }, ...__VLS_functionalComponentArgsRest(__VLS_114));
    let __VLS_117;
    let __VLS_118;
    let __VLS_119;
    const __VLS_120 = {
        onClick: (...[$event]) => {
            __VLS_ctx.$router.push(`/problems/${scope.row.id}`);
        }
    };
    __VLS_116.slots.default;
    var __VLS_116;
}
var __VLS_112;
var __VLS_80;
__VLS_asFunctionalElement(__VLS_intrinsicElements.div, __VLS_intrinsicElements.div)({
    ...{ style: {} },
});
const __VLS_121 = {}.ElPagination;
/** @type {[typeof __VLS_components.ElPagination, typeof __VLS_components.elPagination, ]} */ ;
// @ts-ignore
const __VLS_122 = __VLS_asFunctionalComponent(__VLS_121, new __VLS_121({
    ...{ 'onCurrentChange': {} },
    ...{ 'onSizeChange': {} },
    currentPage: (__VLS_ctx.q.page),
    pageSize: (__VLS_ctx.q.size),
    layout: "total, sizes, prev, pager, next, jumper",
    pageSizes: ([10, 20, 50]),
    total: (__VLS_ctx.total),
}));
const __VLS_123 = __VLS_122({
    ...{ 'onCurrentChange': {} },
    ...{ 'onSizeChange': {} },
    currentPage: (__VLS_ctx.q.page),
    pageSize: (__VLS_ctx.q.size),
    layout: "total, sizes, prev, pager, next, jumper",
    pageSizes: ([10, 20, 50]),
    total: (__VLS_ctx.total),
}, ...__VLS_functionalComponentArgsRest(__VLS_122));
let __VLS_125;
let __VLS_126;
let __VLS_127;
const __VLS_128 = {
    onCurrentChange: (__VLS_ctx.load)
};
const __VLS_129 = {
    onSizeChange: (__VLS_ctx.onSizeChange)
};
var __VLS_124;
var __VLS_3;
var __VLS_dollars;
const __VLS_self = (await import('vue')).defineComponent({
    setup() {
        return {
            q: q,
            list: list,
            total: total,
            load: load,
            onSearch: onSearch,
            onSizeChange: onSizeChange,
            isFav: isFav,
            toggleFav: toggleFav,
        };
    },
});
export default (await import('vue')).defineComponent({
    setup() {
        return {};
    },
});
; /* PartiallyEnd: #4569/main.vue */
