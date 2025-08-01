package gregtech.api.util;

import net.minecraft.item.ItemStack;

import it.unimi.dsi.fastutil.Hash;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * A configurable generator of hashing strategies, allowing for consideration of select properties of ItemStacks when
 * considering equality.
 */
public interface ItemStackHashStrategy extends Hash.Strategy<ItemStack> {

    /**
     * @return a builder object for producing a custom ItemStackHashStrategy.
     */
    static Builder builder() {
        return new Builder();
    }

    /**
     * Generates an ItemStackHash configured to compare every aspect of ItemStacks.
     *
     * @return the ItemStackHashStrategy as described above.
     */
    static ItemStackHashStrategy comparingAll() {
        return builder().compareItem(true)
                .compareCount(true)
                .compareDamage(true)
                .compareTag(true)
                .build();
    }

    /**
     * Generates an ItemStackHash configured to compare every aspect of ItemStacks except the number
     * of items in the stack.
     *
     * @return the ItemStackHashStrategy as described above.
     */
    static ItemStackHashStrategy comparingAllButCount() {
        return builder().compareItem(true)
                .compareDamage(true)
                .compareTag(true)
                .build();
    }

    static ItemStackHashStrategy comparingItemDamageCount() {
        return builder().compareItem(true)
                .compareDamage(true)
                .compareCount(true)
                .build();
    }

    /**
     * Builder pattern class for generating customized ItemStackHashStrategy
     */
    class Builder {

        private boolean item, count, damage, tag, meta;

        /**
         * Defines whether the Item type should be considered for equality.
         *
         * @param choice {@code true} to consider this property, {@code false} to ignore it.
         * @return {@code this}
         */
        public Builder compareItem(boolean choice) {
            item = choice;
            return this;
        }

        /**
         * Defines whether stack size should be considered for equality.
         *
         * @param choice {@code true} to consider this property, {@code false} to ignore it.
         * @return {@code this}
         */
        public Builder compareCount(boolean choice) {
            count = choice;
            return this;
        }

        /**
         * Defines whether damage values should be considered for equality.
         *
         * @param choice {@code true} to consider this property, {@code false} to ignore it.
         * @return {@code this}
         */
        public Builder compareDamage(boolean choice) {
            damage = choice;
            return this;
        }

        /**
         * Defines whether metadata values should be considered for equality.
         *
         * @param choice {@code true} to consider this property, {@code false} to ignore it.
         * @return {@code this}
         */
        public Builder compareMetadata(boolean choice) {
            meta = choice;
            return this;
        }

        /**
         * Defines whether NBT Tags should be considered for equality.
         *
         * @param choice {@code true} to consider this property, {@code false} to ignore it.
         * @return {@code this}
         */
        public Builder compareTag(boolean choice) {
            tag = choice;
            return this;
        }

        /**
         * @return the ItemStackHashStrategy as configured by "compare" methods.
         */
        public ItemStackHashStrategy build() {
            return new ItemStackHashStrategy() {

                @Override
                public int hashCode(@Nullable ItemStack o) {
                    return o == null || o.isEmpty() ? 0 : Objects.hash(
                            item ? o.getItem() : null,
                            count ? o.getCount() : null,
                            damage ? o.getItemDamage() : null,
                            tag ? o.getTagCompound() : null,
                            meta ? o.getMetadata() : null);
                }

                @Override
                public boolean equals(@Nullable ItemStack a, @Nullable ItemStack b) {
                    if (a == null || a.isEmpty()) return b == null || b.isEmpty();
                    if (b == null || b.isEmpty()) return false;

                    return (!item || a.getItem() == b.getItem()) &&
                            (!count || a.getCount() == b.getCount()) &&
                            (!damage || a.getItemDamage() == b.getItemDamage()) &&
                            (!meta || a.getMetadata() == b.getMetadata()) &&
                            (!tag || Objects.equals(a.getTagCompound(), b.getTagCompound()));
                }
            };
        }
    }
}
