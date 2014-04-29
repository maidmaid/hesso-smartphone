package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * La classe AllTests est une classe regroupant tous les autres tests
 * @author /DM/
 */
@RunWith(Suite.class)
@SuiteClasses({ TestBattleSpaceKey.class, TestContact.class })
public class AllTests
{

}
