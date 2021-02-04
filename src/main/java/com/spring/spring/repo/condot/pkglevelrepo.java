package com.spring.spring.repo.condot;

import com.spring.spring.models.condot.tbl_product_pkg_level_details;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface pkglevelrepo extends JpaRepository<tbl_product_pkg_level_details, Long> {
   @Query(value = "  SELECT id,[rT2_0ER02_Server].[dbo].[tbl_Product_Pkg_Level_Details].[Prod_Pkg_ID], [Pkg_Level], [PCMapCount]\n" +
            "               FROM [rT2_0ER02_Server].[dbo].[tbl_Product_Pkg_Level_Details]\n" +
            "   inner join [rT2_0ER02_Server].[dbo].[tbl_Job] on [rT2_0ER02_Server].[dbo].[tbl_Product_Pkg_Level_Details].Prod_Pkg_ID=[rT2_0ER02_Server].[dbo].[tbl_Job].Prod_Pkg_ID\n" +
            "      Where  JID= ?1 \n" +
            "  order by JID desc",nativeQuery = true)
    List<tbl_product_pkg_level_details> findPkgLVL(String jobid);
}
