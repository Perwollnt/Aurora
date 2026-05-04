package gg.auroramc.aurora.expansions.economy.providers;

import gg.auroramc.aurora.api.util.ThreadSafety;
import gg.auroramc.aurora.expansions.economy.AuroraEconomy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import su.nightexpress.excellenteconomy.api.ExcellentEconomyAPI;

public class ExcellentEconomy implements AuroraEconomy {

    RegisteredServiceProvider<ExcellentEconomyAPI> provider = null;

    public ExcellentEconomy() {
        provider = Bukkit.getServer().getServicesManager().getRegistration(ExcellentEconomyAPI.class);
    }

    private String getCurrencyId(String currency) {
        return currency == null || currency.equals("default") ? "coins" : currency;
    }

    @Override
    public void withdraw(Player player, String currency, double amount) {
        this.provider.getProvider().withdraw(player, getCurrencyId(currency), amount);
    }

    @Override
    public void deposit(Player player, String currency, double amount) {
        this.provider.getProvider().deposit(player, getCurrencyId(currency), amount);
    }

    @Override
    public double getBalance(Player player, String currency) {
        return this.provider.getProvider().getBalance(player, getCurrencyId(currency));
    }

    @Override
    public boolean hasBalance(Player player, String currency, double amount) {
        return getBalance(player, currency) >= amount;
    }

    @Override
    public boolean validateCurrency(String currency) {
        return this.provider.getProvider().hasCurrency(currency);
    }

    @Override
    public boolean supportsCurrency() {
        return true;
    }

    @Override
    public ThreadSafety getThreadSafety() {
        return ThreadSafety.SYNC_ONLY;
    }
}
