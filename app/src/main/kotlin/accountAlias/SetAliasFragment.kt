package moe.yuuta.mipushtester.accountAlias

import androidx.core.content.ContextCompat
import moe.yuuta.mipushtester.R
import moe.yuuta.mipushtester.push.internal.PushSdkWrapper

class SetAliasFragment : SetListAbsFragment() {
    override fun loadData(): Set<String> {
        val origSet = AccountAliasStore.get(requireContext()).getAlias()
        if (origSet.isEmpty()) {
            mState.showIcon.set(true)
            mState.showTitle.set(true)
            mState.showDescription.set(true)
            mState.icon.set(ContextCompat.getDrawable(requireContext(), R.mipmap.illustration_list_is_empty))
            mState.text.set(getString(R.string.alias_empty_title))
            mState.showRetry.set(false)
            mState.description.set(getString(R.string.alias_empty_description))
        } else {
            mState.hideAll()
        }
        return origSet
    }

    override fun handleAdd(value: String) {
        AccountAliasStore.get(requireContext())
                .addAlias(value)
        PushSdkWrapper.setAlias(requireContext(), value)
        // Refresh null state
        loadData()
    }

    override fun handleRemove(value: String) {
        AccountAliasStore.get(requireContext())
                .removeAlias(value)
        PushSdkWrapper.unsetAlias(requireContext(), value)
        // Refresh null state
        loadData()
    }

    override fun getDialogSummary(addNew: Boolean): String? =
            if (addNew) getString(R.string.add_alias)
            else getString(R.string.modify_alias)
}