// Generated code from Butter Knife. Do not modify!
package com.sample.sydneyzamoranos;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ItemListActivity_ViewBinding implements Unbinder {
  private ItemListActivity target;

  @UiThread
  public ItemListActivity_ViewBinding(ItemListActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ItemListActivity_ViewBinding(ItemListActivity target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.fab = Utils.findRequiredViewAsType(source, R.id.fab, "field 'fab'", FloatingActionButton.class);
    target.recyclerView = Utils.findRequiredView(source, R.id.item_list, "field 'recyclerView'");
  }

  @Override
  @CallSuper
  public void unbind() {
    ItemListActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.fab = null;
    target.recyclerView = null;
  }
}
