package me.macjuul.chfiles.Functions;

import com.laytonsmith.PureUtilities.SimpleVersion;
import com.laytonsmith.PureUtilities.Version;
import com.laytonsmith.annotations.api;
import com.laytonsmith.core.Security;
import com.laytonsmith.core.constructs.CVoid;
import com.laytonsmith.core.constructs.Construct;
import com.laytonsmith.core.constructs.Target;
import com.laytonsmith.core.environments.Environment;
import com.laytonsmith.core.exceptions.CRE.CREIOException;
import com.laytonsmith.core.exceptions.CRE.CRESecurityException;
import com.laytonsmith.core.exceptions.CRE.CREThrowable;
import com.laytonsmith.core.exceptions.ConfigRuntimeException;
import com.laytonsmith.core.functions.AbstractFunction;

import java.io.File;
import java.io.IOException;

/**
 * Created by bexco on 2016-03-16.
 */
@api
public class create_file extends AbstractFunction {
    @Override
    public Class<? extends CREThrowable>[] thrown() {
        return new Class[]{
                CREIOException.class,
                CRESecurityException.class
        };
    }

    @Override
    public boolean isRestricted() {
        return true;
    }

    @Override
    public Boolean runAsync() {
        return true;
    }

    @Override
    public Construct exec(Target t, Environment env, Construct... args) throws ConfigRuntimeException {
        File loc = new File(t.file().getParentFile(), args[0].val());
        if (!Security.CheckSecurity(loc)) {
            throw new CRESecurityException("You do not have permission to access the file '" + loc.getAbsolutePath() + "'", t);
        }
        try {
            if (loc.exists()) {
                throw new CREIOException(loc.getAbsolutePath() + "Already Exists", t);
            }
            loc.createNewFile();
            return CVoid.VOID;
        } catch (IOException e) {
            throw new CREIOException("File could not be created.", t);
        }
    }

    @Override
    public String getName() {
        return "create_file";
    }

    @Override
    public Integer[] numArgs() {
        return new Integer[]{1};
    }

    @Override
    public String docs() {
        return "{file} void creates a file.";
    }

    @Override
    public Version since() {
        return new SimpleVersion(1, 0, 0);
    }
}
